package com.cromoteca.wasmcf;

import com.cromoteca.wasmcf.components.Div;
import com.cromoteca.wasmcf.components.TextField;
import com.cromoteca.wasmcf.components.Button;
import com.cromoteca.wasmcf.components.Notification;

public class MainView extends Div {
    public MainView() {
        var osDiv = new Div();
        osDiv.setText("System.getProperty(\"os.name\") = " + System.getProperty("os.name"));

        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setPlaceholder("Enter your name here");
        
        var button = new Button("Say Hello");

        add(osDiv, textField, button);

        button.addClickListener(event -> {
            String name = textField.getValue();
            String greeting = name.isEmpty() ? "Hello stranger" : "Hello " + name;
            Notification.show(greeting);
        });
    }
}
