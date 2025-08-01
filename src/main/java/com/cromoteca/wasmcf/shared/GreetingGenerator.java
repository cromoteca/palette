package com.cromoteca.wasmcf.shared;

public class GreetingGenerator {

    public static String generateGreeting(String name) {
        var osName = System.getProperty("os.name");
        var javaVersion = System.getProperty("java.version");
        var nameToUse = (name == null || name.trim().isEmpty()) ? "stranger" : name;
        return "Hello " + nameToUse + " from " + osName + " (Java " + javaVersion + ")";
    }
}
