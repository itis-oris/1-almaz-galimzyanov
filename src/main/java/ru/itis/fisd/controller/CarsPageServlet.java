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
import ru.itis.fisd.model.User;
import ru.itis.fisd.service.CarService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cars")
public class CarsPageServlet extends HttpServlet {
    
    final static Logger logger = LogManager.getLogger(CarsPageServlet.class);

    private final CarService service = new CarService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String page = request.getParameter("page");
            if (page == null) {
                page = "1";
            }
            int limit = 3;
            int offset = (Integer.parseInt(page) - 1) * limit;
            int count = service.count();
            int pages = (int) Math.ceil((double) count / limit);

            List<Integer> cpList = new ArrayList<>();
            for (int i = 1; i <= pages; ++i) {
                cpList.add(i);
            }
            HttpSession session = request.getSession(false);
            long id = 0L;
            if (session != null) {
                User user = (User) session.getAttribute("user");
                id = user.getId() == null ? 0L : user.getId();
            }
            request.setAttribute("cars", service.findAll(limit, offset, id));
            request.setAttribute("pages", cpList);

            request.getRequestDispatcher("/cars.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.info(e.getMessage());
        }
    }
}