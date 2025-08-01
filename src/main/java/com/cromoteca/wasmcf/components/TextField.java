package com.cromoteca.wasmcf.components;

import org.teavm.jso.JSBody;

public class TextField extends Component {
    
    public TextField() {
        super("vaadin-text-field");
    }
    
    public String getValue() {
        return getElementValue(getElement());
    }
    
    public void setValue(String value) {
        setElementValue(getElement(), value);
    }
    
    public void setPlaceholder(String placeholder) {
        setElementPlaceholder(getElement(), placeholder);
    }
    
    public void setLabel(String label) {
        setElementLabel(getElement(), label);
    }
    
    @JSBody(params = {"element"}, script = "return element.value || '';")
    private static native String getElementValue(Object element);
    
    @JSBody(params = {"element", "value"}, script = "element.value = value;")
    private static native void setElementValue(Object element, String value);
    
    @JSBody(params = {"element", "placeholder"}, script = "element.placeholder = placeholder;")
    private static native void setElementPlaceholder(Object element, String placeholder);
    
    @JSBody(params = {"element", "label"}, script = "element.label = label;")
    private static native void setElementLabel(Object element, String label);
}
