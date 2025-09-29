package com.cromoteca.palette.client.views;

import com.cromoteca.palette.shared.GreetingGenerator;
import com.cromoteca.palette.client.components.VerticalLayout;
import com.cromoteca.palette.client.components.HorizontalLayout;
import com.cromoteca.palette.client.components.TextField;
import com.cromoteca.palette.client.components.Button;
import com.cromoteca.palette.client.components.Notification;
import com.cromoteca.palette.client.services.GreetingService;

public class MainView extends VerticalLayout {

    private final GreetingService greetingService = new GreetingService();

    public MainView() {
        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setPlaceholder("Enter your name here");

        var button = new Button("Say Hello (Server)");
        var localButton = new Button("Say Hello (Local)");

        setSpacing("0.75rem");

        var buttonsRow = new HorizontalLayout();
        buttonsRow.setSpacing("0.5rem");
        buttonsRow.add(button, localButton);

        add(textField, buttonsRow);

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
