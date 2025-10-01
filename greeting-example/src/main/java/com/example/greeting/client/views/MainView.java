package com.example.greeting.client.views;

import com.example.greeting.shared.GreetingGenerator;
import com.cromoteca.palette.client.components.VerticalLayout;
import com.cromoteca.palette.client.components.HorizontalLayout;
import com.cromoteca.palette.client.components.TextField;
import com.cromoteca.palette.client.components.Button;
import com.cromoteca.palette.client.components.Notification;
import com.example.greeting.client.services.GreetingService;
import com.cromoteca.palette.client.services.LLMServiceFactory;
import com.cromoteca.palette.client.services.LLMService;
import org.teavm.jso.JSBody;

public class MainView extends VerticalLayout {

    private final GreetingService greetingService = new GreetingService();
    private final LLMService llmService = LLMServiceFactory.getInstance();

    public MainView() {
        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setValue("Luciano");
        textField.setPlaceholder("Enter your name here");

        var button = new Button("Say Hello (Server)");
        var localButton = new Button("Say Hello (Local)");
        var aiButton = new Button("AI Greeting");
        var debugButton = new Button("Debug LLM");

        setSpacing("0.75rem");

        var buttonsRow = new HorizontalLayout();
        buttonsRow.setSpacing("0.5rem");
        buttonsRow.add(button, localButton, aiButton, debugButton);

        add(textField, buttonsRow);

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

            // Show loading notification
            Notification.show("🤖 Generating AI greeting...");

            // Generate AI greeting
            var prompt = "Generate a creative and friendly greeting for someone named " + name + ". Keep it short (1-2 sentences).";
            var aiGreeting = llmService.generate(prompt);

            // Show result with implementation info
            var message = aiGreeting + "\n\n[" + llmService.getImplementationName() + "]";
            Notification.show(message);
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
