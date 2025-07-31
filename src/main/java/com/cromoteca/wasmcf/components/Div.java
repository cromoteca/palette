package com.cromoteca.wasmcf.components;

public class Div extends Component {
    
    public Div() {
        super("div");
    }
    
    public void setText(String text) {
        getElement().setTextContent(text);
    }
    
    public void add(Component... components) {
        for (Component component : components) {
            getElement().appendChild(component.getElement());
            addChild(component);
        }
    }
}
