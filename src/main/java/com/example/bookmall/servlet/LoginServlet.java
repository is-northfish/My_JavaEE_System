package com.example.bookmall.servlet;

import com.example.bookmall.entity.User;
import com.example.bookmall.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            User user = userService.login(username, password);
            if (user == null) {
                forwardWithError(req, resp, "用户名或密码错误", username);
                logResult(req, false, "login_failed");
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            logResult(req, true, "login_ok");
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            forwardWithError(req, resp, "登录失败，请稍后重试", username);
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error, String username)
            throws ServletException, IOException {
        req.setAttribute("error", error);
        req.setAttribute("username", username);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        dispatcher.forward(req, resp);
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("LoginServlet " + uri + " " + result);
        } else {
            System.out.println("LoginServlet " + uri + " " + result + " - " + message);
        }
    }
}
