package com.cromoteca.wasmcf;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.html.HTMLButtonElement;

public class Client {
    
    @JSFunctor
    public interface SayHelloFunction extends JSObject {
        void call(String text);
    }

    private static HTMLInputElement inputElement;
    private static HTMLElement outputElement;
    
    public static void main(String[] args) {
        setJSCallback(Client::sayHello);
        createUserInterface();
    }

    private static void createUserInterface() {
        HTMLDocument document = HTMLDocument.current();
        HTMLElement body = document.getBody();
        
        inputElement = (HTMLInputElement) document.createElement("input");
        inputElement.setType("text");
        inputElement.setPlaceholder("Enter your name");
        inputElement.setId("inputText");
        body.appendChild(inputElement);
        
        HTMLButtonElement button = (HTMLButtonElement) document.createElement("button");
        button.setTextContent("Say Hello");
        button.setId("helloBtn");
        
        button.addEventListener("click", evt -> {
            String text = inputElement.getValue();
            sayHello(text);
        });
        
        body.appendChild(button);
        
        outputElement = document.createElement("div");
        outputElement.setId("outputDiv");
        body.appendChild(outputElement);
    }

    public static void sayHello(String text) {
        String greeting = text.length() > 0 ? "Hello " + text : "Hello stranger";
        outputElement.setTextContent(greeting);
    }
    
    @JSBody(params = {"callback"}, script = "window.javaCallbacks = window.javaCallbacks || {}; window.javaCallbacks.sayHello = callback;")
    private static native void setJSCallback(SayHelloFunction callback);
}
