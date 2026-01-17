package com.example.bookmall.servlet;

import com.example.bookmall.entity.Category;
import com.example.bookmall.service.BookService;
import com.example.bookmall.service.CategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/book/add")
public class AdminBookAddServlet extends HttpServlet {
    private final BookService bookService = new BookService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Category> categories = categoryService.listAll();
            req.setAttribute("categories", categories);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/book_add.jsp");
            dispatcher.forward(req, resp);
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
        String categoryIdParam = req.getParameter("categoryId");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String stock = req.getParameter("stock");

        long categoryId;
        try {
            categoryId = Long.parseLong(categoryIdParam);
        } catch (NumberFormatException e) {
            forwardWithError(req, resp, "分类不合法");
            logResult(req, false, "invalid_category");
            return;
        }

        try {
            String error = bookService.addBook(categoryId, name, price, stock);
            if (error != null) {
                forwardWithError(req, resp, error);
                logResult(req, false, "add_failed");
                return;
            }
            logResult(req, true, "created");
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } catch (SQLException e) {
            forwardWithError(req, resp, "新增图书失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        try {
            List<Category> categories = categoryService.listAll();
            req.setAttribute("categories", categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("error", error);
        req.setAttribute("name", req.getParameter("name"));
        req.setAttribute("price", req.getParameter("price"));
        req.setAttribute("stock", req.getParameter("stock"));
        req.setAttribute("categoryId", req.getParameter("categoryId"));
        req.getRequestDispatcher("/admin/book_add.jsp").forward(req, resp);
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("AdminBookAddServlet " + uri + " " + result);
        } else {
            System.out.println("AdminBookAddServlet " + uri + " " + result + " - " + message);
        }
    }
}
