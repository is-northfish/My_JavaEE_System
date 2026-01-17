package com.example.bookmall.servlet;

import com.example.bookmall.entity.Book;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final CategoryService categoryService = new CategoryService();
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Category> categories = categoryService.listAll();
            Map<Long, List<Book>> booksByCategory = new LinkedHashMap<>();

            for (Category category : categories) {
                List<Book> books = bookService.listByCategory(category.getId());
                booksByCategory.put(category.getId(), books);
            }

            req.setAttribute("categories", categories);
            req.setAttribute("booksByCategory", booksByCategory);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
            logResult(req, true, null);
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("首页加载失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("HomeServlet " + uri + " " + result);
        } else {
            System.out.println("HomeServlet " + uri + " " + result + " - " + message);
        }
    }
}
