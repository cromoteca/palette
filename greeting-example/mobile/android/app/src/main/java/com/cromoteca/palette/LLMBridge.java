package com.cromoteca.palette;

import android.webkit.JavascriptInterface;
import android.content.Context;
import android.util.Log;

/**
 * Simple JavaScript bridge for LLM inference.
 * Exposed directly to WebView without Capacitor plugin complexity.
 */
public class LLMBridge {
    private static final String TAG = "LLMBridge";
    private LLMInference llmInference;
    private Context context;
    private String initError = null;

    public LLMBridge(Context context) {
        this.context = context;
        try {
            Log.i(TAG, "Initializing LLM bridge...");
            this.llmInference = new LLMInference(context);
            Log.i(TAG, "LLM bridge initialized successfully");
        } catch (Throwable e) {  // Catch Throwable to handle native crashes
            Log.e(TAG, "Failed to initialize LLM: " + e.getMessage(), e);
            this.initError = e.getMessage();
            this.llmInference = null;
        }
    }

    @JavascriptInterface
    public String generate(String prompt) {
        if (llmInference == null) {
            return "❌ LLM initialization failed: " + (initError != null ? initError : "Unknown error");
        }
        try {
            return llmInference.generate(prompt);
        } catch (Exception e) {
            Log.e(TAG, "Generation failed: " + e.getMessage(), e);
            return "❌ Generation error: " + e.getMessage();
        }
    }

    @JavascriptInterface
    public boolean isAvailable() {
        return llmInference != null && llmInference.isInitialized();
    }

    @JavascriptInterface
    public String getInfo() {
        if (llmInference == null) {
            return "LLM initialization failed: " + (initError != null ? initError : "Unknown error");
        }
        if (llmInference.isInitialized()) {
            return "MediaPipe Gemma 3 1B";
        } else {
            return "MediaPipe (Not Loaded)";
        }
    }

    /**
     * Start streaming generation.
     * @param requestId Unique ID for this request
     * @param prompt The prompt to generate from
     */
    @JavascriptInterface
    public void generateStreaming(String requestId, String prompt) {
        if (llmInference == null) {
            Log.w(TAG, "Cannot start streaming: LLM not initialized");
            return;
        }
        try {
            llmInference.generateStreaming(requestId, prompt);
        } catch (Exception e) {
            Log.e(TAG, "Streaming generation failed: " + e.getMessage(), e);
        }
    }

    /**
     * Get streaming update for a request.
     * @param requestId The request ID
     * @return JSON string: {"text": "...", "done": true/false}
     */
    @JavascriptInterface
    public String getStreamingUpdate(String requestId) {
        if (llmInference == null) {
            return "{\"text\":\"❌ LLM not initialized\",\"done\":true}";
        }
        try {
            return llmInference.getStreamingUpdate(requestId);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get streaming update: " + e.getMessage(), e);
            return "{\"text\":\"❌ Error: " + e.getMessage() + "\",\"done\":true}";
        }
    }

    /**
     * Clear streaming state for a request.
     * @param requestId The request ID
     */
    @JavascriptInterface
    public void clearStreamingState(String requestId) {
        if (llmInference != null) {
            llmInference.clearStreamingState(requestId);
        }
    }
}
