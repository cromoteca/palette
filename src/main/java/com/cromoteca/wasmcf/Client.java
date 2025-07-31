package com.cromoteca.wasmcf;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

/**
 * Main client class for the WebAssembly application.
 * This class demonstrates how to create a bridge between JavaScript and Java/WASM
 * using TeaVM's JSO (JavaScript Objects) API with callback functions.
 */
public class Client {
    
    /**
     * Functional interface representing a JavaScript callback function that accepts a single string parameter.
     * This interface is used to create a bridge between Java methods and JavaScript functions.
     * The @JSFunctor annotation tells TeaVM to generate the necessary JavaScript interop code.
     */
    @JSFunctor
    public interface SayHelloFunction extends JSObject {
        /**
         * Calls the JavaScript function with the provided text parameter.
         * 
         * @param text the text to pass to the JavaScript callback function
         */
        void call(String text);
    }
    
    /**
     * Entry point of the WebAssembly application.
     * Sets up the JavaScript callback mechanism so that JavaScript code can call Java methods.
     * 
     * @param args command line arguments (not used in this web application)
     */
    public static void main(String[] args) {
        // Set up the callback function for JS to call by creating a method reference
        // and exposing it to JavaScript via window.javaCallbacks.sayHello
        setJSCallback(Client::sayHello);
    }

    /**
     * Processes the input text and updates the HTML page content.
     * This method is called from JavaScript via the callback mechanism.
     * It prepends "Hello " to the input text, or uses "Hello stranger" for empty input.
     * 
     * @param text the input text from the HTML form field
     */
    public static void sayHello(String text) {
        setDivContent(text.length() > 0 ? "Hello " + text : "Hello stranger");
    }
    
    /**
     * Native JavaScript method that registers a Java callback function as a global JavaScript function.
     * This creates window.javaCallbacks.sayHello that can be called from JavaScript code.
     * The callback parameter is the Java method reference that will be called.
     * 
     * @param callback the Java method reference to expose to JavaScript
     */
    @JSBody(params = {"callback"}, script = "window.javaCallbacks = window.javaCallbacks || {}; window.javaCallbacks.sayHello = callback;")
    private static native void setJSCallback(SayHelloFunction callback);

    /**
     * Native JavaScript method that updates the content of the HTML div with id 'outputDiv'.
     * This demonstrates direct DOM manipulation from Java/WASM code.
     * 
     * @param content the text content to display in the output div
     */
    @JSBody(params = {"content"}, script = "document.getElementById('outputDiv').textContent = content;")
    private static native void setDivContent(String content);
}
