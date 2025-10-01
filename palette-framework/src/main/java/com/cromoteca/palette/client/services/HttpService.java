package com.cromoteca.palette.client.services;

import org.teavm.jso.JSBody;

public class HttpService {
    
    public static String get(String url) {
        try {
            return makeGetRequest(url);
        } catch (Exception e) {
            // Return a friendly error message for mobile/offline scenarios
            return "Error: Server not available (mobile/offline mode)";
        }
    }
    
    @JSBody(params = {"url"}, script =
        "try {" +
        "  var xhr = new XMLHttpRequest();" +
        "  xhr.open('GET', url, false);" +
        "  xhr.send();" +
        "  if (xhr.status === 200) {" +
        "    return xhr.responseText;" +
        "  } else {" +
        "    throw new Error('HTTP ' + xhr.status);" +
        "  }" +
        "} catch (e) {" +
        "  throw new Error('Network error: ' + e.message);" +
        "}")
    private static native String makeGetRequest(String url);
}
