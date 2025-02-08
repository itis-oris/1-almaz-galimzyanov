package ru.itis.fisd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.UserService;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private static final UserService userService = new UserService();

    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("register.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {

            String login_value = request.getParameter(NAME);
            String password_value = request.getParameter(PASSWORD);
            String role_value = request.getParameter(ROLE);

            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            password_value = bCrypt.encode(password_value);

            User user = new User(0L, login_value, password_value, role_value, 0L);
            userService.insertUser(user);

            response.sendRedirect(request.getContextPath() + "/main");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}