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

@WebServlet("/mycars")
public class MyCarsPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(MyCarsPageServlet.class);

    private final CarService service = new CarService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            String page = request.getParameter("page");
            if (page == null) {
                page = "1";
            }
            int limit = 3;
            int count;
            int offset = (Integer.parseInt(page) - 1) * limit;
            if (user.getRole().equals("seller")) {
                count = service.countBySeller(user.getId());
            } else {
                count = service.countByUser(user.getId());
            }
            logger.info(count);
            logger.info(Math.ceil((double) count / limit));
            int pages = (int) Math.ceil((double) count / limit);

            List<Integer> cpList = new ArrayList<>();
            for (int i = 1; i <= pages; ++i) {
                cpList.add(i);
            }


            long id = user.getId();
            if (user.getRole().equals("seller")) {
                request.setAttribute("cars", service.findBySeller(limit, offset, id));
            } else {
                request.setAttribute("cars", service.findByUser(limit, offset, id));
            }
            logger.info(cpList);
            request.setAttribute("pages", cpList);

            request.getRequestDispatcher("/mycars.ftl").forward(request, response);
        } catch (IOException | ServletException e) {
            logger.info(e.getMessage());
        }
    }
}
