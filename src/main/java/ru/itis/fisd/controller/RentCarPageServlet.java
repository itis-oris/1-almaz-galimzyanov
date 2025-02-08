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
import ru.itis.fisd.model.Rent;
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.CarService;
import ru.itis.fisd.service.RentService;
import ru.itis.fisd.service.UserService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/rent")
public class RentCarPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(RentCarPageServlet.class);

    private static final CarService carService = new CarService();
    private static final RentService rentService = new RentService();
    private static final UserService userService = new UserService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            request.setAttribute("id", request.getParameter("car"));
            request.setAttribute("name", request.getParameter("name"));

            request.getRequestDispatcher("/rent_car.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long car_id = Long.valueOf(request.getParameter("carId"));
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            String customer_name = user.getName();

            Car car = carService.findById(car_id);

            List<Rent> rents = rentService.findAll(car_id);
            logger.info("RENTS" + rents);
            Date dateStart = Date.valueOf(request.getParameter("rentalStartDate"));
            Date dateEnd = Date.valueOf(request.getParameter("rentalEndDate"));
            logger.info(dateStart + " " + dateEnd);
            boolean flag = true;
            for (Rent rent : rents) {
                logger.info(dateStart.after(rent.getDateEnd()));
                logger.info(dateEnd.before(rent.getDateStart()));
                if (!(dateStart.after(rent.getDateEnd()) || dateEnd.before(rent.getDateStart()))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {

                Long id = new UserService().getByName(customer_name).getId();
                logger.info(customer_name);
                logger.info(request.getParameter("rentalStartDate"));

                Rent rent = new Rent(0L, id, car_id, dateStart, dateEnd);

                rentService.insert(rent);
                long diffInMillis = dateEnd.getTime() - dateStart.getTime();
                long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                Long price = (long) ((double) car.getPrice() / 365 * diffInDays);
                userService.updateBill(user.getId(), price);
                car.setAvailable(false);
                carService.update(car);
            } else {
                logger.info("RENT IS NOT AVAILABLE");
            }
            response.sendRedirect(request.getContextPath() + "/main");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}