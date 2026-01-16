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

        resp.setContentType("text/html;charset=UTF-8");

        try {
            Book book = bookService.findById(id);
            if (book == null) {
                writeNotFound(resp);
                logResult(req, false, "not_found");
                return;
            }

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"/>");
            html.append("<title>图书详情</title></head><body>");
            html.append("<h2>图书详情</h2>");
            html.append("<p>书名：").append(book.getName()).append("</p>");
            html.append("<p>价格：").append(book.getPrice()).append("</p>");
            html.append("<p>库存：").append(book.getStock()).append("</p>");
            html.append("<p>分类ID：").append(book.getCategoryId()).append("</p>");
            html.append("<p><a href=\"/\">返回首页</a></p>");
            html.append("</body></html>");

            resp.getWriter().write(html.toString());
            logResult(req, true, "ok");
        } catch (SQLException e) {
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
