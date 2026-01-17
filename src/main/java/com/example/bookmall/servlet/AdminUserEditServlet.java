package com.example.bookmall.servlet;

import com.example.bookmall.entity.User;
import com.example.bookmall.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/user/edit")
public class AdminUserEditServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
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
            User user = userService.getUserById(id);
            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().write("用户不存在");
                logResult(req, false, "not_found");
                return;
            }
            req.setAttribute("user", user);
            req.getRequestDispatcher("/admin/user_edit.jsp").forward(req, resp);
            logResult(req, true, "loaded");
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("加载用户失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "参数不合法");
            logResult(req, false, "invalid_id");
            return;
        }

        try {
            String error = userService.updateUser(id, username, password);
            if (error != null) {
                req.setAttribute("error", error);
                User user = userService.getUserById(id);
                req.setAttribute("user", user);
                req.getRequestDispatcher("/admin/user_edit.jsp").forward(req, resp);
                logResult(req, false, "update_failed");
                return;
            }
            logResult(req, true, "updated");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("更新用户失败");
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
            System.out.println("AdminUserEditServlet " + uri + " " + result);
        } else {
            System.out.println("AdminUserEditServlet " + uri + " " + result + " - " + message);
        }
    }
}
