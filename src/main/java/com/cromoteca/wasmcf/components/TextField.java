package com.cromoteca.wasmcf.components;

import org.teavm.jso.dom.html.HTMLInputElement;

public class TextField extends Component {
    
    public TextField() {
        super("input");
        HTMLInputElement input = (HTMLInputElement) getElement();
        input.setType("text");
    }
    
    public String getValue() {
        HTMLInputElement input = (HTMLInputElement) getElement();
        return input.getValue();
    }
    
    public void setValue(String value) {
        HTMLInputElement input = (HTMLInputElement) getElement();
        input.setValue(value);
    }
    
    public void setPlaceholder(String placeholder) {
        HTMLInputElement input = (HTMLInputElement) getElement();
        input.setPlaceholder(placeholder);
    }
}
