package com.cromoteca.palette.client.services;

/**
 * Service for running Large Language Model inference.
 * Provides platform-specific implementations:
 * - Browser: Mock/fake responses
 * - Android: On-device inference via MediaPipe
 */
public interface LLMService {

    /**
     * Generate text from a prompt.
     *
     * @param prompt The input text prompt
     * @return Generated text response
     */
    String generate(String prompt);

    /**
     * Check if LLM is available on this platform.
     *
     * @return true if LLM inference is available
     */
    boolean isAvailable();

    /**
     * Get the name/type of the LLM implementation.
     *
     * @return Implementation name (e.g., "Mock", "MediaPipe-Gemma2B")
     */
    String getImplementationName();
}
