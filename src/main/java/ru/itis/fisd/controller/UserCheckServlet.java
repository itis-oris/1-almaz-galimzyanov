package ru.itis.fisd.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.UserService;

import java.io.IOException;

@WebServlet("/usercheck")
public class UserCheckServlet extends HttpServlet {

    private static final UserService userService = new UserService();

    private static final String NAME = "name";
    private static final String PASSWORD = "password";


    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {

            String login_value = request.getParameter(NAME);
            String password_value = request.getParameter(PASSWORD);
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

            User user = userService.getByName(login_value);


            if (user != null && bCrypt.matches(password_value, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/main");
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}