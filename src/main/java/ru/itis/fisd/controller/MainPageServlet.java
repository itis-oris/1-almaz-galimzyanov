package ru.itis.fisd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.fisd.model.User;

import java.io.IOException;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            User user = session == null ? null : (User) session.getAttribute("user");

            request.setAttribute("user", user);

            request.getRequestDispatcher("main.ftl").forward(request, response);

        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
