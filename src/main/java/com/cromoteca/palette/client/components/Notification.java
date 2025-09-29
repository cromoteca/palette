package com.cromoteca.palette.client.components;

import org.teavm.jso.JSBody;

public class Notification {
    
    /**
     * Shows a notification with the given text message.
     * The notification will be displayed for 5 seconds by default.
     * 
     * @param text the text to display in the notification
     */
    public static void show(String text) {
        show(text, "primary", 5000);
    }

    public static void show(String text, String variant, int durationMs) {
        showInternal(text, variant, durationMs);
    }

    @JSBody(params = {"text", "variant", "duration"}, script =
        "var alert = document.createElement('sl-alert');" +
        "alert.variant = variant || 'primary';" +
        "alert.closable = true;" +
        "alert.innerHTML = text;" +
        // Auto hide if duration > 0
        "if (duration > 0) { setTimeout(function(){ if(alert.parentNode){ alert.hide && alert.hide(); alert.remove(); } }, duration); }" +
        "document.body.appendChild(alert);" +
        // Wait for definition then toast
        "if (customElements.get('sl-alert')) { alert.toast(); } else { customElements.whenDefined('sl-alert').then(()=>alert.toast()); }")
    private static native void showInternal(String text, String variant, int durationMs);
}
