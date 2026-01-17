package com.example.bookmall.servlet;

import com.example.bookmall.entity.Book;
import com.example.bookmall.entity.Category;
import com.example.bookmall.service.BookService;
import com.example.bookmall.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/book/edit")
public class AdminBookEditServlet extends HttpServlet {
    private final BookService bookService = new BookService();
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
            Book book = bookService.findById(id);
            if (book == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().write("图书不存在");
                logResult(req, false, "not_found");
                return;
            }
            List<Category> categories = categoryService.listAll();
            req.setAttribute("book", book);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/admin/book_edit.jsp").forward(req, resp);
            logResult(req, true, "loaded");
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("加载图书失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String categoryIdParam = req.getParameter("categoryId");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String stock = req.getParameter("stock");

        long id;
        long categoryId;
        try {
            id = Long.parseLong(idParam);
            categoryId = Long.parseLong(categoryIdParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "参数不合法");
            logResult(req, false, "invalid_param");
            return;
        }

        try {
            String error = bookService.updateBook(id, categoryId, name, price, stock);
            if (error != null) {
                req.setAttribute("error", error);
                Book book = bookService.findById(id);
                List<Category> categories = categoryService.listAll();
                req.setAttribute("book", book);
                req.setAttribute("categories", categories);
                req.getRequestDispatcher("/admin/book_edit.jsp").forward(req, resp);
                logResult(req, false, "update_failed");
                return;
            }
            logResult(req, true, "updated");
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("更新图书失败");
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
            System.out.println("AdminBookEditServlet " + uri + " " + result);
        } else {
            System.out.println("AdminBookEditServlet " + uri + " " + result + " - " + message);
        }
    }
}
