package com.example.bookmall;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) count = 0;
        session.setAttribute("count", count + 1);

        req.setAttribute("msg", "Hello Servlet/JSP! session count=" + (count + 1));
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
