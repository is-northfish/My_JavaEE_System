package com.example.bookmall.servlet;

import com.example.bookmall.entity.Book;
import com.example.bookmall.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/book")
public class BookDetailServlet extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // 1. 设置响应编码 (必须在 getWriter 之前)
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            writeBadRequest(resp, "缺少图书参数");
            return;
        }

        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "图书参数不合法");
            return;
        }

        try {
            Book book = bookService.findById(id);
            if (book == null) {
                writeNotFound(resp);
                return;
            }

            // === 诊断逻辑：开始 ===
            String dbName = book.getName();
            System.out.println("----- DEBUG START -----");
            System.out.println("1. [Static Test] 静态中文测试: 技术"); 
            System.out.println("2. [DB Raw Name] 数据库原始读取: " + dbName);
            
            // 字节取样：查看数据库出来的 String 在内存里到底是什么
            StringBuilder hex = new StringBuilder();
            byte[] bytes = dbName.getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                hex.append(String.format("%02X ", b));
            }
            System.out.println("3. [DB Name Hex] UTF-8 字节序列: " + hex.toString());
            System.out.println("----- DEBUG END -------");
            // === 诊断逻辑：结束 ===

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"/>");
            html.append("<title>图书详情</title></head><body>");
            html.append("<h2>图书详情 (Debug Mode)</h2>");
            html.append("<hr/>");
            html.append("<p>书名 (来自数据库)：<strong>").append(dbName).append("</strong></p>");
            html.append("<p>价格：").append(book.getPrice()).append("</p>");
            html.append("<p><a href=\"/\">返回首页</a></p>");
            html.append("</body></html>");

            resp.getWriter().write(html.toString());
            
        } catch (SQLException e) {
            resp.getWriter().write("数据库查询失败");
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
}