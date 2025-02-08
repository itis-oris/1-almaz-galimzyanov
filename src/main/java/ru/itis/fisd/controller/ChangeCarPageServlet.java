package ru.itis.fisd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.fisd.model.Car;
import ru.itis.fisd.service.CarService;

import java.io.IOException;

@WebServlet("/change")
public class ChangeCarPageServlet extends HttpServlet {

    private static final CarService carService = new CarService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int id = Integer.parseInt(request.getParameter("car"));

            request.setAttribute("id", id);
            request.getRequestDispatcher("/change.ftl").forward(request, response);

        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            long id = Long.parseLong(request.getParameter("id"));
            Car car = carService.findById(id);
            car.setName(name);
            car.setPrice(price);
            carService.update(car);

            response.sendRedirect(request.getContextPath() + "/mycars");
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
