package com.cromoteca.wasmcf;

import org.teavm.jso.dom.html.HTMLDocument;
import com.cromoteca.wasmcf.client.views.MainView;

public class Application {
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
