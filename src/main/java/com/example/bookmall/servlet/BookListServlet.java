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

@WebServlet("/books")
public class BookListServlet extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String categoryIdParam = req.getParameter("categoryId");
        if (categoryIdParam == null || categoryIdParam.trim().isEmpty()) {
            writeBadRequest(resp, "缺少分类参数");
            logResult(req, false, "missing_category");
            return;
        }

        long categoryId;
        try {
            categoryId = Long.parseLong(categoryIdParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "分类参数不合法");
            logResult(req, false, "invalid_category");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");

        try {
            List<Book> books = bookService.listByCategory(categoryId);

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"/>");
            html.append("<title>图书列表</title></head><body>");
            html.append("<h2>分类 ").append(categoryId).append(" 的图书</h2>");
            if (books.isEmpty()) {
                html.append("<p>暂无图书</p>");
            } else {
                html.append("<ul>");
                for (Book book : books) {
                    html.append("<li>")
                        .append(book.getName())
                        .append(" - ")
                        .append(book.getPrice())
                        .append(" (库存: ")
                        .append(book.getStock())
                        .append(")</li>");
                }
                html.append("</ul>");
            }
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

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("BookListServlet " + uri + " " + result);
        } else {
            System.out.println("BookListServlet " + uri + " " + result + " - " + message);
        }
    }
}
