package com.example.lab1;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        logRequest(request);
        String choice = request.getParameter("pageChoice");
        
        if (isDesktopApp(request.getHeader("User-Agent"))) {
            response.setContentType("text/plain");
            response.getWriter().println("Parameter value: " + choice);
        } else {
            request.getRequestDispatcher("/page" + choice + ".html").forward(request, response);
        }
    }
    
    private void logRequest(HttpServletRequest request) {
        System.out.println("Method: " + request.getMethod() + 
                          ", IP: " + request.getRemoteAddr() + 
                          ", User-Agent: " + request.getHeader("User-Agent") + 
                          ", Language: " + request.getHeader("Accept-Language") + 
                          ", Parameter: " + request.getParameter("pageChoice"));
    }
    
    private boolean isDesktopApp(String userAgent) {
        return userAgent == null || (!userAgent.contains("Mozilla") && !userAgent.contains("Chrome"));
    }
}