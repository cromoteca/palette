package com.cromoteca.palette.client.services;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import java.util.function.Consumer;

/**
 * Android LLM implementation using MediaPipe via Capacitor plugin.
 * Bridges Java calls to native Android MediaPipe inference.
 *
 * Note: Uses a global cache for async results since TeaVM/WASM doesn't support async/await.
 */
public class AndroidLLMService implements LLMService {

    private static int requestCounter = 0;

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

    /**
     * Generate streaming response with callback for updates.
     * Polls the native bridge for updates.
     */
    public void generateStreaming(String prompt, Consumer<String> onUpdate, Runnable onComplete) {
        String requestId = "req_" + (++requestCounter);

        // Start streaming on Android
        startStreamingGeneration(requestId, prompt);

        // Poll for updates
        pollStreamingUpdates(requestId, onUpdate, onComplete);
    }

    @JSBody(params = {"requestId", "prompt"}, script =
        "if (typeof AndroidLLM === 'undefined') {" +
        "  throw new Error('AndroidLLM bridge not available');" +
        "}" +
        "AndroidLLM.generateStreaming(requestId, prompt);")
    private static native void startStreamingGeneration(String requestId, String prompt);

    private void pollStreamingUpdates(String requestId, Consumer<String> onUpdate, Runnable onComplete) {
        pollUpdate(requestId, onUpdate, onComplete);
    }

    @JSFunctor
    private interface JsStringCallback extends JSObject {
        void run(String value);
    }

    @JSFunctor
    private interface JsVoidCallback extends JSObject {
        void run();
    }

    private void pollUpdate(String requestId, Consumer<String> onUpdate, Runnable onComplete) {
        // Use setInterval to poll (TeaVM/browser compatible)
        JsStringCallback jsOnUpdate = onUpdate == null ? null : onUpdate::accept;
        JsVoidCallback jsOnComplete = onComplete == null ? null : onComplete::run;
        startPolling(requestId, jsOnUpdate, jsOnComplete);
    }

    @JSBody(params = {"requestId", "onUpdate", "onComplete"}, script =
        "var lastText = '';" +
        "console.log('Starting polling for request:', requestId);" +
        "var intervalId = setInterval(function() {" +
        "  if (typeof AndroidLLM === 'undefined') {" +
        "    console.error('AndroidLLM not available');" +
        "    clearInterval(intervalId);" +
        "    return;" +
        "  }" +
        "  try {" +
        "    var json = AndroidLLM.getStreamingUpdate(requestId);" +
        "    console.log('Received update:', json);" +
        "    var update = JSON.parse(json);" +
        "    var currentText = update.text || '';" +
        "    if (currentText !== lastText) {" +
        "      console.log('Text changed, calling onUpdate');" +
        "      lastText = currentText;" +
        "      onUpdate(currentText);" +
        "    }" +
        "    if (update.done) {" +
        "      console.log('Streaming complete');" +
        "      clearInterval(intervalId);" +
        "      AndroidLLM.clearStreamingState(requestId);" +
        "      onComplete();" +
        "    }" +
        "  } catch(e) {" +
        "    console.error('Polling error:', e);" +
        "    clearInterval(intervalId);" +
        "  }" +
        "}, 100);")
    private static native void startPolling(String requestId, JsStringCallback onUpdate, JsVoidCallback onComplete);
}
