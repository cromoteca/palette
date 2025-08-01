package com.cromoteca.wasmcf.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/greeting")
public class GreetingServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        
        // Set response headers for JSON and CORS
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        // Generate server-side greeting
        String greeting = generateGreeting(name);
        
        // Return JSON response
        PrintWriter out = response.getWriter();
        out.print("{\"greeting\": \"" + escapeJson(greeting) + "\", \"timestamp\": " + System.currentTimeMillis() + "}");
        out.flush();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle POST requests (for more complex data)
        doGet(request, response);
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle CORS preflight requests
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    private String generateGreeting(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Hello stranger! Server time: " + new java.util.Date();
        }
        
        // Server-side logic - you can add database calls, business logic, etc.
        String[] greetings = {
            "Hello", "Hi", "Greetings", "Welcome", "Good to see you"
        };
        String randomGreeting = greetings[(int) (Math.random() * greetings.length)];
        
        return randomGreeting + " " + name + "! (from server at " + new java.util.Date() + ")";
    }
    
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
