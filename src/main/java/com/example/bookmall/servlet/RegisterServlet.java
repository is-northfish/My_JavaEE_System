package com.example.bookmall.servlet;

import com.example.bookmall.entity.User;
import com.example.bookmall.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (confirmPassword == null || !confirmPassword.equals(password)) {
            forwardWithError(req, resp, "两次输入的密码不一致", username);
            logResult(req, false, "password_mismatch");
            return;
        }

        try {
            User user = userService.register(username, password);
            if (user == null) {
                forwardWithError(req, resp, "用户名已存在或注册失败", username);
                logResult(req, false, "register_failed");
                return;
            }

            logResult(req, true, "registered");
            resp.sendRedirect("login.jsp");
        } catch (IllegalArgumentException e) {
            forwardWithError(req, resp, e.getMessage(), username);
            logResult(req, false, "invalid_input");
        } catch (SQLException e) {
            forwardWithError(req, resp, "注册失败，请稍后重试", username);
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error, String username)
            throws ServletException, IOException {
        req.setAttribute("error", error);
        req.setAttribute("username", username);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/register.jsp");
        dispatcher.forward(req, resp);
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("RegisterServlet " + uri + " " + result);
        } else {
            System.out.println("RegisterServlet " + uri + " " + result + " - " + message);
        }
    }
}
