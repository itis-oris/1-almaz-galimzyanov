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
import ru.itis.fisd.service.PurchaseService;
import ru.itis.fisd.service.RentService;

import java.io.IOException;

@WebServlet("/car_item")
public class CarItemPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(CarItemPageServlet.class);

    private final CarService service = new CarService();
    private final RentService rentService = new RentService();
    private final PurchaseService purchaseService = new PurchaseService();


    private static final String CAR = "car";
    private static final String USER = "user";

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            User user = session == null ? null : (User) session.getAttribute(USER);

            long id = Long.parseLong(request.getParameter(CAR));
            long user_id = 0L;
            if (user != null) {
                user_id = user.getId() == null ? 0L : user.getId();
                request.setAttribute(USER, user);
            } else {
                User guest = new User(0L, "guest", "123", "guest", 0L);
                request.setAttribute(USER, guest);
            }

            Long car_id = rentService.findById(user_id, id);
            Long car_id2 = purchaseService.findById(user_id, id);

            logger.info(user_id + " " + car_id);
            if (car_id == null && car_id2 == null) {
                request.setAttribute("forRent", "true");
            } else {
                request.setAttribute("forRent", "false");
            }

            Car car = service.findById(id);
            request.setAttribute(CAR, car);

            request.getRequestDispatcher("/car_item.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.info(e.getMessage());
        }
    }
}