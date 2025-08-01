package com.cromoteca.wasmcf.server;

import com.cromoteca.wasmcf.shared.GreetingGenerator;
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
        
        try (var out = response.getWriter()) {
            out.print(GreetingGenerator.generateGreeting(name));
        }
    }
}
