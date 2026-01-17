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

@WebServlet("/book")
public class BookDetailServlet extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            writeBadRequest(resp, "缺少图书参数");
            logResult(req, false, "missing_id");
            return;
        }

        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "图书参数不合法");
            logResult(req, false, "invalid_id");
            return;
        }

        try {
            Book book = bookService.findById(id);
            if (book == null) {
                writeNotFound(resp);
                logResult(req, false, "not_found");
                return;
            }

            req.setAttribute("book", book);
            req.getRequestDispatcher("/book_detail.jsp").forward(req, resp);
            logResult(req, true, "ok");
            
        } catch (SQLException e) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("数据库查询失败");
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    private void writeBadRequest(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(message);
    }

    private void writeNotFound(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("图书不存在");
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("BookDetailServlet " + uri + " " + result);
        } else {
            System.out.println("BookDetailServlet " + uri + " " + result + " - " + message);
        }
    }
}
