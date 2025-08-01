package com.cromoteca.wasmcf;

import org.teavm.jso.dom.html.HTMLDocument;

public class Client {
    public static void main(String[] args) {
        createUserInterface();
    }

    private static void createUserInterface() {
        var document = HTMLDocument.current();
        var body = document.getBody();
        
        var mainView = new MainView();
        body.appendChild(mainView.getElement());
    }
}
