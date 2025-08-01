package com.cromoteca.wasmcf;

import com.cromoteca.wasmcf.components.Div;
import com.cromoteca.wasmcf.components.TextField;
import com.cromoteca.wasmcf.components.Button;

public class MainView extends Div {
    public MainView() {
        var osDiv = new Div();
        osDiv.setText("System.getProperty(\"os.name\") = " + System.getProperty("os.name"));

        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setPlaceholder("Enter your name here");
        
        var button = new Button("Say Hello");
        var output = new Div();

        add(osDiv, textField, button, output);

        button.addClickListener(event -> {
            String name = textField.getValue();
            String greeting = name.isEmpty() ? "Hello stranger" : "Hello " + name;
            output.setText(greeting);
        });
    }
}
