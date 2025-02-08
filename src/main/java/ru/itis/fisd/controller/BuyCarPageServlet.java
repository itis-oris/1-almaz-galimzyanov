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
import ru.itis.fisd.model.Purchase;
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.CarService;
import ru.itis.fisd.service.PurchaseService;
import ru.itis.fisd.service.UserService;

import java.io.IOException;

@WebServlet("/buy")
public class BuyCarPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(BuyCarPageServlet.class);

    private static final PurchaseService purchaseService = new PurchaseService();
    private static final CarService carService = new CarService();
    private static final UserService userService = new UserService();

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String CAR = "car";
    private static final String CAR_ID = "carId";
    private static final String USER = "user";

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            request.setAttribute(ID, request.getParameter(CAR));
            request.setAttribute(NAME, request.getParameter(NAME));

            request.getRequestDispatcher("/buy_car.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.info(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long car_id = Long.valueOf(request.getParameter(CAR_ID));
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute(USER);
            String customer_name = user.getName();

            Car car = carService.findById(car_id);

            if (car.isAvailable()) {
                Long id = new UserService().getByName(customer_name).getId();
                Purchase purchase = new Purchase(0L, id, car_id, request.getParameter("paymentMethod"));

                purchaseService.insert(purchase);
                Long price = (long) car.getPrice();
                userService.updateBill(user.getId(), price);
                car.setAvailable(false);
                car.setForSale(false);
                carService.update(car);

            } else {
                logger.info("Car is not available");
            }
            response.sendRedirect(request.getContextPath() + "/main");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}