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

@WebServlet("/pay")
public class PayPageServlet extends HttpServlet {

    private static final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        User real = userService.getByName(user.getName());
        request.setAttribute("user", real);
        request.getRequestDispatcher("pay.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            long payValue = Long.parseLong(request.getParameter("paymentAmount"));

            userService.updateBill(user.getId(), -payValue);

            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
