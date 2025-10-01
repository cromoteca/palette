package com.example.greeting.client.views;

import com.example.greeting.shared.GreetingGenerator;
import com.cromoteca.palette.client.components.VerticalLayout;
import com.cromoteca.palette.client.components.HorizontalLayout;
import com.cromoteca.palette.client.components.TextField;
import com.cromoteca.palette.client.components.Button;
import com.cromoteca.palette.client.components.Notification;
import com.cromoteca.palette.client.components.Div;
import com.example.greeting.client.services.GreetingService;
import com.cromoteca.palette.client.services.LLMServiceFactory;
import com.cromoteca.palette.client.services.AndroidLLMService;
import org.teavm.jso.JSBody;

public class MainView extends VerticalLayout {

    private final GreetingService greetingService = new GreetingService();
    private final AndroidLLMService llmService;

    public MainView() {
        // Cast to AndroidLLMService to access streaming
        llmService = (AndroidLLMService) LLMServiceFactory.getInstance();

        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setValue("Luciano");
        textField.setPlaceholder("Enter your name here");

        var button = new Button("Say Hello (Server)");
        var localButton = new Button("Say Hello (Local)");
        var aiButton = new Button("AI Greeting (Streaming)");
        var debugButton = new Button("Debug LLM");

        // Div for streaming LLM output
        var llmOutputDiv = new Div();
        llmOutputDiv.getElement().setClassName("llm-output");
        llmOutputDiv.getElement().getStyle().setProperty("padding", "1rem");
        llmOutputDiv.getElement().getStyle().setProperty("margin-top", "1rem");
        llmOutputDiv.getElement().getStyle().setProperty("border", "1px solid #ccc");
        llmOutputDiv.getElement().getStyle().setProperty("border-radius", "4px");
        llmOutputDiv.getElement().getStyle().setProperty("min-height", "100px");
        llmOutputDiv.getElement().getStyle().setProperty("white-space", "pre-wrap");
        llmOutputDiv.getElement().getStyle().setProperty("font-family", "system-ui");
        llmOutputDiv.setText("AI response will appear here...");

        setSpacing("0.75rem");

        var helloButtons = new HorizontalLayout();
        helloButtons.setSpacing("0.5rem");
        helloButtons.add(button, localButton);

        var llmButtons = new HorizontalLayout();
        llmButtons.setSpacing("0.5rem");
        llmButtons.add(aiButton, debugButton);

        add(textField, helloButtons, llmButtons, llmOutputDiv);

        button.addClickListener(event -> {
            var greeting = greetingService.generateGreeting(textField.getValue());
            Notification.show(greeting);
        });

        localButton.addClickListener(event -> {
            var name = textField.getValue();
            Notification.show(GreetingGenerator.generateGreeting(name));
        });

        aiButton.addClickListener(event -> {
            var name = textField.getValue();
            if (name == null || name.trim().isEmpty()) {
                name = "friend";
            }

            // Clear output and show loading
            llmOutputDiv.setText("🤖 Generating...\n\n");

            // Generate AI greeting with streaming
            var prompt = "Generate a creative and friendly greeting for someone named " + name + ". Keep it short (1-2 sentences).";

            llmService.generateStreaming(
                prompt,
                // onUpdate callback - called for each token/chunk
                (text) -> llmOutputDiv.setText(text),
                // onComplete callback - called when done
                () -> {
                    var currentText = llmOutputDiv.getElement().getTextContent();
                    llmOutputDiv.setText(currentText + "\n\n✅ [" + llmService.getImplementationName() + "]");
                }
            );
        });

        debugButton.addClickListener(event -> {
            var debugInfo = new StringBuilder();
            debugInfo.append("LLM Implementation: ").append(llmService.getImplementationName()).append("\n\n");
            debugInfo.append("Available: ").append(llmService.isAvailable()).append("\n\n");
            debugInfo.append("Platform: ").append(getPlatformInfo()).append("\n\n");
            debugInfo.append("Capacitor: ").append(checkCapacitor()).append("\n\n");
            debugInfo.append("LLM Plugin: ").append(checkLLMPlugin());
            Notification.show(debugInfo.toString());
        });
    }

    @JSBody(params = {}, script =
        "if (typeof Capacitor !== 'undefined') {" +
        "  return 'Capacitor: ' + Capacitor.getPlatform();" +
        "}" +
        "return 'Browser (no Capacitor)';")
    private static native String getPlatformInfo();

    @JSBody(params = {}, script =
        "return typeof Capacitor !== 'undefined' ? 'Available' : 'Not found';")
    private static native String checkCapacitor();

    @JSBody(params = {}, script =
        "if (typeof AndroidLLM !== 'undefined') {" +
        "  return 'AndroidLLM bridge available!';" +
        "}" +
        "if (typeof Capacitor !== 'undefined' && Capacitor.Plugins && Capacitor.Plugins.LLMPlugin) {" +
        "  return 'Capacitor LLMPlugin available!';" +
        "}" +
        "return 'No LLM bridge found';")
    private static native String checkLLMPlugin();
}
