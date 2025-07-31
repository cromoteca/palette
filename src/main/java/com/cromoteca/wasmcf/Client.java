package com.cromoteca.wasmcf;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

public class Client {
    public static void main(String[] args) {
        createUserInterface();
    }

    private static void createUserInterface() {
        HTMLDocument document = HTMLDocument.current();
        HTMLElement body = document.getBody();
        
        MainView mainView = new MainView();
        body.appendChild(mainView.getElement());
    }
}
