package com.cromoteca.palette;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

/**
 * Capacitor plugin for LLM inference using MediaPipe.
 */
@CapacitorPlugin(name = "LLMPlugin")
public class LLMPlugin extends Plugin {

    private LLMInference llmInference;

    @Override
    public void load() {
        // Initialize MediaPipe LLM on plugin load
        llmInference = new LLMInference(getContext());
    }

    /**
     * Generate text from a prompt.
     */
    @PluginMethod
    public void generate(PluginCall call) {
        String prompt = call.getString("prompt");

        if (prompt == null || prompt.isEmpty()) {
            call.reject("Prompt is required");
            return;
        }

        if (llmInference == null || !llmInference.isInitialized()) {
            call.reject("LLM not initialized");
            return;
        }

        try {
            String result = llmInference.generate(prompt);
            JSObject ret = new JSObject();
            ret.put("text", result);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("LLM generation failed: " + e.getMessage());
        }
    }

    /**
     * Get LLM implementation info.
     */
    @PluginMethod
    public void getInfo(PluginCall call) {
        JSObject ret = new JSObject();
        if (llmInference != null && llmInference.isInitialized()) {
            ret.put("name", "MediaPipe Gemma 3 1B");
            ret.put("available", true);
        } else {
            ret.put("name", "MediaPipe (Not Loaded)");
            ret.put("available", false);
        }
        call.resolve(ret);
    }

    /**
     * Check if LLM is available.
     */
    @PluginMethod
    public void isAvailable(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("available", llmInference != null && llmInference.isInitialized());
        call.resolve(ret);
    }
}
