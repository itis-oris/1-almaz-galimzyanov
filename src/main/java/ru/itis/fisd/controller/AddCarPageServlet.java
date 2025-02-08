package ru.itis.fisd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.fisd.database.DBConnection;
import ru.itis.fisd.model.Car;
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.CarService;

import java.io.IOException;

@WebServlet("/addCar")
public class AddCarPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(AddCarPageServlet.class);
    

    private static final CarService carService = new CarService();

    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String USER = "user";

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/add_car.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.info(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute(USER);
            String name = request.getParameter(NAME);
            int price = Integer.parseInt(request.getParameter(PRICE));

            Car car = new Car(0, user.getId(), name, price, true, true);
            carService.save(car);

            response.sendRedirect(request.getContextPath() + "/mycars");

        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}