package com.example.bookmall.servlet;

import com.example.bookmall.service.CategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/category/add")
public class AdminCategoryAddServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/category_add.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            forwardWithError(req, resp, "分类名称不能为空");
            logResult(req, false, "name_empty");
            return;
        }

        try {
            long id = categoryService.addCategory(name);
            if (id <= 0) {
                forwardWithError(req, resp, "新增分类失败");
                logResult(req, false, "insert_failed");
                return;
            }
            logResult(req, true, "created");
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } catch (SQLException e) {
            forwardWithError(req, resp, "新增分类失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        req.setAttribute("error", error);
        req.setAttribute("name", req.getParameter("name"));
        req.getRequestDispatcher("/admin/category_add.jsp").forward(req, resp);
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("AdminCategoryAddServlet " + uri + " " + result);
        } else {
            System.out.println("AdminCategoryAddServlet " + uri + " " + result + " - " + message);
        }
    }
}
