package com.cromoteca.wasmcf.client.views;

import com.cromoteca.wasmcf.shared.GreetingGenerator;
import com.cromoteca.wasmcf.client.components.Div;
import com.cromoteca.wasmcf.client.components.TextField;
import com.cromoteca.wasmcf.client.components.Button;
import com.cromoteca.wasmcf.client.components.Notification;
import com.cromoteca.wasmcf.client.services.GreetingService;

public class MainView extends Div {
    private final GreetingService greetingService = new GreetingService();
    
    public MainView() {
        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setPlaceholder("Enter your name here");
        
        var button = new Button("Say Hello (Server)");
        var localButton = new Button("Say Hello (Local)");

        add(textField, button, localButton);

        button.addClickListener(event -> {
            var greeting = greetingService.generateGreeting(textField.getValue());
            Notification.show(greeting);
        });
        
        localButton.addClickListener(event -> {
            var name = textField.getValue();
            Notification.show(GreetingGenerator.generateGreeting(name));
        });
    }
}
