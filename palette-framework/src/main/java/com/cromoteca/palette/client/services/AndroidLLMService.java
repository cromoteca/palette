package com.cromoteca.palette.client.services;

import org.teavm.jso.JSBody;

/**
 * Android LLM implementation using MediaPipe via Capacitor plugin.
 * Bridges Java calls to native Android MediaPipe inference.
 *
 * Note: Uses a global cache for async results since TeaVM/WASM doesn't support async/await.
 */
public class AndroidLLMService implements LLMService {

    @Override
    public String generate(String prompt) {
        try {
            // Start async generation
            startGeneration(prompt);

            // Poll for result with timeout
            int maxAttempts = 300; // 30 seconds (100ms * 300)
            for (int i = 0; i < maxAttempts; i++) {
                if (isGenerationComplete()) {
                    String result = getGenerationResult();
                    clearGenerationCache();
                    return result;
                }
                sleep(100); // Wait 100ms
            }

            clearGenerationCache();
            return "⏱️ LLM generation timeout (30s exceeded)";

        } catch (Exception e) {
            clearGenerationCache();
            return "Error: LLM not available. " + e.getMessage();
        }
    }

    @Override
    public boolean isAvailable() {
        return checkAndroidLLMAvailable();
    }

    @Override
    public String getImplementationName() {
        return "Android MediaPipe LLM";
    }

    /**
     * Start async LLM generation, storing result in global cache.
     * Uses direct WebView JavaScript bridge instead of Capacitor plugin.
     */
    @JSBody(params = {"prompt"}, script =
        "if (typeof AndroidLLM === 'undefined') {" +
        "  throw new Error('AndroidLLM bridge not available');" +
        "}" +
        "window._llmCache = { done: false, result: null, error: null };" +
        "try {" +
        "  var result = AndroidLLM.generate(prompt);" +
        "  window._llmCache.result = result;" +
        "  window._llmCache.done = true;" +
        "} catch(e) {" +
        "  window._llmCache.error = e.message || 'Unknown error';" +
        "  window._llmCache.done = true;" +
        "}")
    private static native void startGeneration(String prompt);

    /**
     * Check if generation is complete.
     */
    @JSBody(params = {}, script =
        "return window._llmCache && window._llmCache.done === true;")
    private static native boolean isGenerationComplete();

    /**
     * Get generation result (check isComplete first).
     */
    @JSBody(params = {}, script =
        "if (!window._llmCache) return 'No result';" +
        "if (window._llmCache.error) throw new Error(window._llmCache.error);" +
        "return window._llmCache.result || 'Empty result';")
    private static native String getGenerationResult();

    /**
     * Clear the generation cache.
     */
    @JSBody(params = {}, script =
        "window._llmCache = null;")
    private static native void clearGenerationCache();

    /**
     * Sleep for specified milliseconds.
     */
    @JSBody(params = {"ms"}, script =
        "var start = Date.now();" +
        "while (Date.now() - start < ms) { /* busy wait */ }")
    private static native void sleep(int ms);

    /**
     * Check if Android LLM bridge is available.
     */
    @JSBody(params = {}, script =
        "return typeof AndroidLLM !== 'undefined';")
    private static native boolean checkAndroidLLMAvailable();
}
