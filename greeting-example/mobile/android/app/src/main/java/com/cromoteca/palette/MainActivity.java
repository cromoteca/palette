package com.cromoteca.palette;

import android.os.Bundle;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add LLM bridge to WebView
        WebView webView = getBridge().getWebView();
        webView.addJavascriptInterface(new LLMBridge(this), "AndroidLLM");
    }
}
