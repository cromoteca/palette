package com.cromoteca.wasmcf.components;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    private final HTMLElement element;
    private final List<Component> children = new ArrayList<>();
    
    protected Component(String tagName) {
        var document = HTMLDocument.current();
        this.element = document.createElement(tagName);
    }
    
    public HTMLElement getElement() {
        return element;
    }
    
    protected void addChild(Component child) {
        children.add(child);
    }
    
    protected List<Component> getChildren() {
        return children;
    }
}
