package com.example.ServletTest.filter;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String role = (String) request.getSession().getAttribute("role");
        if (role != null && role.equals("admin")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        request.getRequestDispatcher("index.jsp").forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
