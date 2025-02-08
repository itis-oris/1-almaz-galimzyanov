package ru.itis.fisd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.UserService;

import java.io.IOException;

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {


    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/main");
            return;
        }

        request.setAttribute("user", user);

        request.getRequestDispatcher("editProfile.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/main");
            return;
        }


        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");
        int id = Integer.parseInt(request.getParameter("id"));

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        newPassword = bCrypt.encode(newPassword);

        user.setName(newUsername);
        user.setPassword(newPassword);

        boolean success = userService.updateUser(id, user);

        if (success) {
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.setAttribute("errorMessage", "Failed to update profile");
            request.getRequestDispatcher("editProfile.ftl").forward(request, response);
        }
    }
}
