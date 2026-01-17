package com.example.bookmall.servlet;

import com.example.bookmall.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/user/add")
public class AdminUserAddServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/admin/user_add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            String error = userService.addUser(username, password);
            if (error != null) {
                forwardWithError(req, resp, error);
                logResult(req, false, "add_failed");
                return;
            }
            logResult(req, true, "created");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } catch (SQLException e) {
            forwardWithError(req, resp, "新增用户失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        req.setAttribute("error", error);
        req.setAttribute("username", req.getParameter("username"));
        req.getRequestDispatcher("/admin/user_add.jsp").forward(req, resp);
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("AdminUserAddServlet " + uri + " " + result);
        } else {
            System.out.println("AdminUserAddServlet " + uri + " " + result + " - " + message);
        }
    }
}
