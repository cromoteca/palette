package com.cromoteca.palette.client.services;

public class GreetingService {

    public String generateGreeting(String name) {
        var url = "/api/greeting?name=" + encodeURIComponent(name);
        return HttpService.get(url);
    }
    
    private String encodeURIComponent(String str) {
        if (str == null) return "";
        // Simple URL encoding for basic characters
        return str.replace(" ", "%20").replace("&", "%26").replace("=", "%3D");
    }
}
