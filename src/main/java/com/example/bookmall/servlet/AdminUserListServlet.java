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
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserListServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<User> users = userService.listAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/admin/user_list.jsp").forward(req, resp);
            logResult(req, true, null);
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("用户列表加载失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("AdminUserListServlet " + uri + " " + result);
        } else {
            System.out.println("AdminUserListServlet " + uri + " " + result + " - " + message);
        }
    }
}
