package com.cromoteca.wasmcf;

import com.cromoteca.wasmcf.components.Div;
import com.cromoteca.wasmcf.components.TextField;
import com.cromoteca.wasmcf.components.Button;
import com.cromoteca.wasmcf.components.Notification;

public class MainView extends Div {
    private final GreetingService greetingService = new GreetingService();
    
    public MainView() {
        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setPlaceholder("Enter your name here");
        
        var button = new Button("Say Hello (Server)");
        var localButton = new Button("Say Hello (Local)");

        add(textField, button, localButton);

        // Server-side greeting
        button.addClickListener(event -> {
            var name = textField.getValue();
            var greeting = greetingService.generateGreeting(name);
            Notification.show(greeting);
        });
        
        // Local greeting (original functionality)
        localButton.addClickListener(event -> {
            var name = textField.getValue();
            var osName = System.getProperty("os.name");
            var greeting = name.isEmpty() ? "Hello stranger" : "Hello " + name;
            Notification.show(greeting + " from " + osName + " (local)");
        });
    }
}
