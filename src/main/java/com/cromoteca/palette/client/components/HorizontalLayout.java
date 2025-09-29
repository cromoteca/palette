package com.cromoteca.palette.client.components;

import org.teavm.jso.JSBody;

/**
 * A flex row container that arranges its child components horizontally.
 */
public class HorizontalLayout extends Layout {

    public HorizontalLayout() {
        super("div");
        applyBaseStyles(getElement());
    }

    @JSBody(params = {"element"}, script =
        "element.style.display='flex';" +
        "element.style.flexDirection='row';" +
        "element.style.alignItems='center';")
    private static native void applyBaseStyles(Object element);
}
