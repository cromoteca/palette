package com.cromoteca.wasmcf.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/greeting")
public class GreetingServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        var name = request.getParameter("name");
        
        // Set response headers for plain text and CORS
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        // Generate server-side greeting
        var greeting = generateGreeting(name);
        
        // Return plain text response
        try (var out = response.getWriter()) {
            out.print(greeting);
        }
    }
    
    private String generateGreeting(String name) {
        var osName = System.getProperty("os.name");
        var nameToUse = (name == null || name.trim().isEmpty()) ? "stranger" : name;
        return "Hello " + nameToUse + " from " + osName;
    }
}
