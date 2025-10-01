package com.cromoteca.palette.client.services;

import org.teavm.jso.JSBody;

/**
 * Factory for creating the appropriate LLM service based on platform.
 */
public class LLMServiceFactory {

    private static LLMService instance;

    /**
     * Get the LLM service instance for the current platform.
     *
     * @return Platform-specific LLM service
     */
    public static LLMService getInstance() {
        if (instance == null) {
            instance = createService();
        }
        return instance;
    }

    private static LLMService createService() {
        if (isAndroid()) {
            return new AndroidLLMService();
        }
        // Browser/web platform uses mock
        return new BrowserLLMService();
    }

    /**
     * Detect if running on Android via Capacitor.
     */
    @JSBody(params = {}, script =
        "return typeof Capacitor !== 'undefined' && " +
        "       Capacitor.getPlatform && " +
        "       Capacitor.getPlatform() === 'android';")
    private static native boolean isAndroid();
}
