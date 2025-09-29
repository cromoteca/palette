package com.cromoteca.palette.client.components;

import org.teavm.jso.JSBody;

/**
 * Base layout component providing spacing configuration shared by concrete layouts.
 */
public abstract class Layout extends Component {

    protected Layout(String tagName) {
        super(tagName);
    }

    /**
     * Sets the spacing (gap) between direct child components in CSS length units (e.g. "0.75rem" or "12px").
     */
    public void setSpacing(String gap) {
        setStyleProperty(getElement(), "gap", gap);
    }

    /**
     * Convenience for numeric pixel gap.
     */
    public void setSpacing(int pixels) {
        setSpacing(pixels + "px");
    }

    /**
     * Adds child components.
     */
    public void add(Component... components) {
        for (var c : components) {
            getElement().appendChild(c.getElement());
            addChild(c);
        }
    }

    @JSBody(params = {"element", "name", "value"}, script = "element.style[name] = value;")
    private static native void setStyleProperty(Object element, String name, String value);
}
