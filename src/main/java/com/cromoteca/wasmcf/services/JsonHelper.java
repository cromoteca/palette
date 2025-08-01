package com.cromoteca.wasmcf.services;

import org.teavm.jso.JSBody;

public class JsonHelper {
    
    public static String getString(String json, String key) {
        return getStringValue(json, key);
    }
    
    @JSBody(params = {"json", "key"}, script =
        "var obj = JSON.parse(json);" +
        "return obj[key];")
    private static native String getStringValue(String json, String key);
}
