package com.example.bookmall.servlet;

import com.example.bookmall.entity.Book;
import com.example.bookmall.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/books")
public class AdminBookListServlet extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Book> books = bookService.listAll();
            req.setAttribute("books", books);
            req.getRequestDispatcher("/admin/book_list.jsp").forward(req, resp);
            logResult(req, true, null);
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("图书列表加载失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("AdminBookListServlet " + uri + " " + result);
        } else {
            System.out.println("AdminBookListServlet " + uri + " " + result + " - " + message);
        }
    }
}
