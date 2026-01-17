package com.example.bookmall.servlet;

import com.example.bookmall.entity.Category;
import com.example.bookmall.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/categories")
public class AdminCategoryListServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Category> categories = categoryService.listAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/admin/category_list.jsp").forward(req, resp);
            logResult(req, true, null);
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("分类列表加载失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("AdminCategoryListServlet " + uri + " " + result);
        } else {
            System.out.println("AdminCategoryListServlet " + uri + " " + result + " - " + message);
        }
    }
}
