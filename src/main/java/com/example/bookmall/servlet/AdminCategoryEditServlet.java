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

@WebServlet("/admin/category/edit")
public class AdminCategoryEditServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

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
            Category category = categoryService.getCategory(id);
            if (category == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().write("分类不存在");
                logResult(req, false, "not_found");
                return;
            }
            req.setAttribute("category", category);
            req.getRequestDispatcher("/admin/category_edit.jsp").forward(req, resp);
            logResult(req, true, "loaded");
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("加载分类失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "参数不合法");
            logResult(req, false, "invalid_id");
            return;
        }

        try {
            String error = categoryService.updateCategory(id, name);
            if (error != null) {
                req.setAttribute("error", error);
                Category category = categoryService.getCategory(id);
                req.setAttribute("category", category);
                req.getRequestDispatcher("/admin/category_edit.jsp").forward(req, resp);
                logResult(req, false, "update_failed");
                return;
            }
            logResult(req, true, "updated");
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("更新分类失败");
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
            System.out.println("AdminCategoryEditServlet " + uri + " " + result);
        } else {
            System.out.println("AdminCategoryEditServlet " + uri + " " + result + " - " + message);
        }
    }
}
