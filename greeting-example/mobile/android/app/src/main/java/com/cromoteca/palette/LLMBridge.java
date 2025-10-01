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
}
