package ru.itis.fisd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.fisd.service.CarService;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteCarPageServlet extends HttpServlet {


    private static final CarService carService = new CarService();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            Long id = Long.parseLong(request.getParameter("car"));

            carService.delete(id);

            response.sendRedirect(request.getContextPath() + "/mycars");
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
