package com.cromoteca.palette.client.components;

import org.teavm.jso.JSBody;

/**
 * A flex column container that arranges its child components vertically.
 */
public class VerticalLayout extends Layout {

    public VerticalLayout() {
        super("div");
        applyBaseStyles(getElement());
    }

    @JSBody(params = {"element"}, script =
        "element.style.display='flex';" +
        "element.style.flexDirection='column';" +
        "element.style.alignItems='flex-start';")
    private static native void applyBaseStyles(Object element);
}
