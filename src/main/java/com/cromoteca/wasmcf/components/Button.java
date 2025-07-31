package com.cromoteca.wasmcf.components;

import java.util.ArrayList;
import java.util.List;

public class Button extends Component {
    private final List<ClickListener> clickListeners = new ArrayList<>();
    
    public Button(String text) {
        super("button");
        getElement().setTextContent(text);
        setupClickHandler();
    }
    
    public void setText(String text) {
        getElement().setTextContent(text);
    }
    
    public void addClickListener(ClickListener listener) {
        clickListeners.add(listener);
    }
    
    private void setupClickHandler() {
        getElement().addEventListener("click", evt -> {
            ClickEvent event = new ClickEvent(this);
            for (ClickListener listener : clickListeners) {
                listener.clicked(event);
            }
        });
    }
    
    public interface ClickListener {
        void clicked(ClickEvent event);
    }
    
    public static class ClickEvent {
        private final Button source;
        
        public ClickEvent(Button source) {
            this.source = source;
        }
        
        public Button getSource() {
            return source;
        }
    }
}
