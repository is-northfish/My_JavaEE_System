package com.example.bookmall.servlet;

import com.example.bookmall.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/user/disable")
public class AdminUserDisableServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "参数不合法");
            logResult(req, false, "invalid_id");
            return;
        }

        try {
            String error = userService.disableUser(id);
            if (error != null) {
                req.setAttribute("error", error);
                req.getRequestDispatcher("/admin/users").forward(req, resp);
                logResult(req, false, "disable_failed");
                return;
            }
            logResult(req, true, "disabled");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("禁用用户失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void writeBadRequest(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(message);
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("AdminUserDisableServlet " + uri + " " + result);
        } else {
            System.out.println("AdminUserDisableServlet " + uri + " " + result + " - " + message);
        }
    }
}
