package com.cromoteca.wasmcf;

import com.cromoteca.wasmcf.components.Div;
import com.cromoteca.wasmcf.components.TextField;
import com.cromoteca.wasmcf.components.Button;
import com.cromoteca.wasmcf.components.Notification;
import com.cromoteca.wasmcf.services.HttpService;
import com.cromoteca.wasmcf.services.JsonHelper;

public class MainView extends Div {
    public MainView() {
        var textField = new TextField();
        textField.setLabel("Your name");
        textField.setPlaceholder("Enter your name here");
        
        var button = new Button("Say Hello (Server)");
        var localButton = new Button("Say Hello (Local)");

        add(textField, button, localButton);

        // Server-side greeting
        button.addClickListener(event -> {
            String name = textField.getValue();
            String url = "/api/greeting?name=" + encodeURIComponent(name);
            String response = HttpService.get(url);
            String greeting = JsonHelper.getString(response, "greeting");
            Notification.show(greeting);
        });
        
        // Local greeting (original functionality)
        localButton.addClickListener(event -> {
            String name = textField.getValue();
            String osName = System.getProperty("os.name");
            String greeting = name.isEmpty() ? "Hello stranger" : "Hello " + name;
            Notification.show(greeting + " from " + osName + " (local)");
        });
    }
    
    private String encodeURIComponent(String str) {
        if (str == null) return "";
        // Simple URL encoding for basic characters
        return str.replace(" ", "%20").replace("&", "%26").replace("=", "%3D");
    }
}
