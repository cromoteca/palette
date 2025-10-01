package com.cromoteca.palette;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.mediapipe.tasks.genai.llminference.LlmInference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Wrapper for MediaPipe LLM inference.
 * Handles model loading and text generation.
 *
 * Expects Gemma 3 1B model file at: assets/models/gemma3-1b-it-int4.task
 */
public class LLMInference {
    private static final String TAG = "LLMInference";
    private static final String MODEL_ASSET_PATH = "models/gemma3-1b-it-int4.task";

    private LlmInference llmInference;
    private boolean initialized = false;
    private Context context;

    // Streaming state
    private static class StreamingState {
        String currentText = "";
        boolean done = false;
    }
    private Map<String, StreamingState> streamingStates = new ConcurrentHashMap<>();

    public LLMInference(Context context) {
        this.context = context;
        initializeModel();
    }

    /**
     * Initialize MediaPipe LLM model.
     * Automatically loads model from assets if present.
     */
    private void initializeModel() {
        try {
            Log.i(TAG, "=== Starting Gemma 3 1B model initialization ===");

            // Check if model exists in assets
            Log.i(TAG, "Checking for model file: " + MODEL_ASSET_PATH);
            String modelPath = getModelPath();
            if (modelPath == null) {
                Log.w(TAG, "Model file not found in assets: " + MODEL_ASSET_PATH);
                Log.w(TAG, "See ANDROID_LLM_SETUP.md for download instructions");
                initialized = false;
                return;
            }

            Log.i(TAG, "Model found at: " + modelPath);
            File modelFile = new File(modelPath);
            Log.i(TAG, "Model file size: " + modelFile.length() + " bytes");
            Log.i(TAG, "Model file exists: " + modelFile.exists());
            Log.i(TAG, "Model file readable: " + modelFile.canRead());

            // Create MediaPipe LLM options (using Google's recommended settings)
            Log.i(TAG, "Building MediaPipe options...");
            LlmInference.LlmInferenceOptions options =
                LlmInference.LlmInferenceOptions.builder()
                    .setModelPath(modelPath)
                    .setMaxTopK(64)  // Recommended by Google docs
                    .build();

            Log.i(TAG, "Options built successfully");
            Log.i(TAG, "Calling MediaPipe createFromOptions (this may take time)...");

            // Initialize LLM
            llmInference = LlmInference.createFromOptions(context, options);
            initialized = true;

            Log.i(TAG, "=== Gemma 3 1B model initialized successfully! ===");

        } catch (Throwable e) {
            Log.e(TAG, "=== Failed to initialize LLM ===");
            Log.e(TAG, "Error type: " + e.getClass().getName());
            Log.e(TAG, "Error message: " + e.getMessage(), e);
            initialized = false;
        }
    }

    /**
     * Get model path, copying from assets to cache if needed.
     * Returns null if model doesn't exist in assets.
     */
    private String getModelPath() {
        try {
            AssetManager assetManager = context.getAssets();

            // Check if model exists in assets
            boolean exists = false;
            try {
                InputStream is = assetManager.open(MODEL_ASSET_PATH);
                is.close();
                exists = true;
            } catch (Exception e) {
                // Model not in assets
                return null;
            }

            // Copy to cache directory for MediaPipe to access
            File cacheDir = context.getCacheDir();
            String modelFileName = new File(MODEL_ASSET_PATH).getName();
            File modelFile = new File(cacheDir, modelFileName);

            // Only copy if not already cached
            if (!modelFile.exists()) {
                Log.i(TAG, "Copying model to cache directory...");
                InputStream is = assetManager.open(MODEL_ASSET_PATH);
                FileOutputStream fos = new FileOutputStream(modelFile);

                byte[] buffer = new byte[8192];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                fos.flush();
                fos.close();
                is.close();
                Log.i(TAG, "Model copied successfully");
            } else {
                Log.i(TAG, "Using cached model file");
            }

            return modelFile.getAbsolutePath();

        } catch (Exception e) {
            Log.e(TAG, "Error getting model path: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Generate text from a prompt (synchronous).
     */
    public String generate(String prompt) {
        if (!initialized || llmInference == null) {
            return "⚠️ LLM model not initialized.\n\n" +
                   "Place gemma3-1b-it-int4.task in android/app/src/main/assets/models/\n" +
                   "See ANDROID_LLM_SETUP.md for download instructions.\n\n" +
                   "Your prompt was: \"" + prompt + "\"";
        }

        try {
            // Synchronous generation
            String response = llmInference.generateResponse(prompt);
            return response;
        } catch (Exception e) {
            Log.e(TAG, "Generation failed: " + e.getMessage(), e);
            return "Error generating response: " + e.getMessage();
        }
    }

    /**
     * Start streaming generation (async).
     * Call getStreamingUpdate() to poll for updates.
     *
     * Note: For now using synchronous generation in background thread
     * since MediaPipe streaming API seems unavailable in this version.
     */
    public void generateStreaming(String requestId, String prompt) {
        if (!initialized || llmInference == null) {
            StreamingState state = new StreamingState();
            state.currentText = "⚠️ LLM model not initialized";
            state.done = true;
            streamingStates.put(requestId, state);
            return;
        }

        Log.i(TAG, "Starting streaming generation for request: " + requestId);
        StreamingState state = new StreamingState();
        streamingStates.put(requestId, state);

        // Generate in background thread and simulate streaming
        new Thread(() -> {
            try {
                Log.i(TAG, "Generating response...");
                String fullResponse = llmInference.generateResponse(prompt);

                // Simulate streaming by sending chunks
                String[] words = fullResponse.split(" ");
                StringBuilder accumulated = new StringBuilder();

                for (String word : words) {
                    accumulated.append(word).append(" ");
                    StreamingState s = streamingStates.get(requestId);
                    if (s != null) {
                        s.currentText = accumulated.toString();
                        Log.d(TAG, "Chunk (" + requestId + "): " + s.currentText);
                    }

                    try {
                        Thread.sleep(50); // Small delay between words
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                // Mark as done
                StreamingState s = streamingStates.get(requestId);
                if (s != null) {
                    s.currentText = fullResponse; // Ensure we have the complete text
                    s.done = true;
                    Log.i(TAG, "Streaming complete for request: " + requestId);
                }

            } catch (Exception e) {
                Log.e(TAG, "Streaming generation failed: " + e.getMessage(), e);
                StreamingState s = streamingStates.get(requestId);
                if (s != null) {
                    s.currentText = "Error: " + e.getMessage();
                    s.done = true;
                    Log.e(TAG, "Streaming error for " + requestId + ": " + e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Get current streaming state.
     * Returns JSON: {"text": "...", "done": true/false}
     */
    public String getStreamingUpdate(String requestId) {
        StreamingState state = streamingStates.get(requestId);
        if (state == null) {
            return "{\"text\":\"\",\"done\":false}";
        }

        // Escape quotes in text
        String escapedText = state.currentText.replace("\"", "\\\"").replace("\n", "\\n");
        return "{\"text\":\"" + escapedText + "\",\"done\":" + state.done + "}";
    }

    /**
     * Clear streaming state for a request.
     */
    public void clearStreamingState(String requestId) {
        streamingStates.remove(requestId);
        Log.i(TAG, "Cleared streaming state for request: " + requestId);
    }

    /**
     * Check if LLM is initialized and ready.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Clean up resources.
     */
    public void close() {
        if (llmInference != null) {
            llmInference.close();
            llmInference = null;
        }
        initialized = false;
    }
}
