package com.cromoteca.wasmcf.client.services;

import org.teavm.jso.JSBody;

public class HttpService {
    
    public static String get(String url) {
        return makeGetRequest(url);
    }
    
    @JSBody(params = {"url"}, script =
        "var xhr = new XMLHttpRequest();" +
        "xhr.open('GET', url, false);" +
        "xhr.send();" +
        "return xhr.responseText;")
    private static native String makeGetRequest(String url);
}
