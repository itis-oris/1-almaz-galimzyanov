package ru.itis.fisd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.UserService;

import java.io.IOException;

@WebServlet("/profile")
public class ProfilePageServlet extends HttpServlet {
    private static final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");



        if (user == null) {
            request.getRequestDispatcher("main.ftl").forward(request, response);
        } else {
            User real = userService.getByName(user.getName());
            request.setAttribute("user", real);
            request.getRequestDispatcher("profile.ftl").forward(request, response);
        }

    }
}
