package com.cromoteca.wasmcf;

import com.cromoteca.wasmcf.components.Div;
import com.cromoteca.wasmcf.components.TextField;
import com.cromoteca.wasmcf.components.Button;

public class MainView extends Div {
    public MainView() {
        var textField = new TextField();
        var button = new Button("Say Hello");
        var output = new Div();

        add(textField, button, output);

        button.addClickListener(event -> {
            String name = textField.getValue();
            String greeting = name.isEmpty() ? "Hello stranger" : "Hello " + name;
            output.setText(greeting);
        });
    }
}
