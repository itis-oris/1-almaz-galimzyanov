package ru.itis.fisd.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.itis.fisd.model.User;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getServletPath().startsWith("/static/") ||
            httpServletRequest.getServletPath().startsWith("/usercheck") ||
            httpServletRequest.getServletPath().startsWith("/register") ||
            httpServletRequest.getServletPath().startsWith("/login") ||
            httpServletRequest.getServletPath().startsWith("/submit_login") ||
            httpServletRequest.getServletPath().startsWith("/cars") ||
            httpServletRequest.getServletPath().startsWith("/car_item")
        ) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null && !httpServletRequest.getServletPath().equals("/")) {
                User user = (User) session.getAttribute("user");
                if (user.getRole().equals("seller") && (
                        httpServletRequest.getServletPath().startsWith("/addCar") ||
                        httpServletRequest.getServletPath().startsWith("/change"))) {
                    filterChain.doFilter(request, response);
                } else if (httpServletRequest.getServletPath().startsWith("/addCar") ||
                           httpServletRequest.getServletPath().startsWith("/change")) {
                    request.getRequestDispatcher("/main").forward(request, response);
                } else if (user.getRole().equals("customer") && (
                        httpServletRequest.getServletPath().startsWith("/return"))) {
                    filterChain.doFilter(request, response);
                } else if (httpServletRequest.getServletPath().startsWith("/return")) {
                    request.getRequestDispatcher("/main").forward(request, response);
                }
                else {
                    filterChain.doFilter(request, response);
                }
            } else {
                request.getRequestDispatcher("/main").forward(request, response);
            }
        }
    }
}
