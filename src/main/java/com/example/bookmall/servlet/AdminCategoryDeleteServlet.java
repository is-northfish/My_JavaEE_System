package com.example.bookmall.servlet;

import com.example.bookmall.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/category/delete")
public class AdminCategoryDeleteServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();

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
            String error = categoryService.deleteCategory(id);
            if (error != null) {
                forwardWithError(req, resp, error);
                logResult(req, false, "delete_failed");
                return;
            }
            logResult(req, true, "deleted");
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } catch (SQLException e) {
            forwardWithError(req, resp, "删除失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("/admin/categories").forward(req, resp);
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
            System.out.println("AdminCategoryDeleteServlet " + uri + " " + result);
        } else {
            System.out.println("AdminCategoryDeleteServlet " + uri + " " + result + " - " + message);
        }
    }
}
