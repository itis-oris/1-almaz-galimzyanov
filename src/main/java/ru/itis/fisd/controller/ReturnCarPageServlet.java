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
import ru.itis.fisd.service.RentService;
import ru.itis.fisd.service.UserService;

import java.io.IOException;

@WebServlet("/return")
public class ReturnCarPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(ReturnCarPageServlet.class);

    private static final RentService rentService = new RentService();
    private static final CarService carService = new CarService();
    private static final UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            request.setAttribute("id", request.getParameter("car"));
            request.setAttribute("name", request.getParameter("name"));

            request.getRequestDispatcher("/return_car.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            Long car_id = Long.valueOf(request.getParameter("carId"));

            Car car = carService.findById(car_id);
            long price = 0L;
            String[] damages = request.getParameterValues("damage");
            if (damages != null) {
                price += (long) damages.length * (car.getPrice() / 10);
            }


            userService.updateBill(user.getId(), price);
            car.setAvailable(true);
            carService.update(car);
            logger.info(car.getId());
            rentService.delete(car, user.getId());
            response.sendRedirect(request.getContextPath() + "/main");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
