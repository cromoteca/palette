package com.cromoteca.wasmcf.components;

import org.teavm.jso.JSBody;

public class Notification {
    
    /**
     * Shows a notification with the given text message.
     * The notification will be displayed for 5 seconds by default.
     * 
     * @param text the text to display in the notification
     */
    public static void show(String text) {
        showNotification(text);
    }
    
    @JSBody(params = {"text"}, script = 
        "var notification = document.createElement('vaadin-notification');" +
        "notification.renderer = function(root) { root.textContent = text; };" +
        "notification.duration = 5000;" +
        "notification.position = 'bottom-start';" +
        "notification.opened = true;" +
        "document.body.appendChild(notification);")
    private static native void showNotification(String text);
}
