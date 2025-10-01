package com.cromoteca.palette.client.services;

/**
 * Mock LLM implementation for browser/web environments.
 * Returns canned responses for testing purposes.
 */
public class BrowserLLMService implements LLMService {

    private static final String[] RESPONSES = {
        "That's an interesting question! As a mock LLM, I can only provide simulated responses.",
        "I understand what you're asking. In a real implementation, this would use actual AI inference.",
        "Great point! This is a placeholder response from the browser mock LLM.",
        "I'm processing your request... Just kidding! I'm a fake LLM for browser testing.",
        "Fascinating! In production, you'd see real AI-generated content here."
    };

    private int responseIndex = 0;

    @Override
    public String generate(String prompt) {
        // Cycle through canned responses
        String response = RESPONSES[responseIndex];
        responseIndex = (responseIndex + 1) % RESPONSES.length;

        return "🔧 [MOCK LLM] " + response + "\n\nYour prompt was: \"" + prompt + "\"";
    }

    @Override
    public boolean isAvailable() {
        return true; // Mock is always available
    }

    @Override
    public String getImplementationName() {
        return "Browser Mock LLM";
    }
}
