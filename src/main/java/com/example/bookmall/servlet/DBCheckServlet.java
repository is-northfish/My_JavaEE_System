package com.example.bookmall.servlet;

import com.example.bookmall.entity.DBStatus;
import com.example.bookmall.service.DBCheckService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 数据库连接测试页面
 * 访问 /dbCheck 可以验证数据库连接是否正常
 */
@WebServlet("/dbCheck")
public class DBCheckServlet extends HttpServlet {
    private final DBCheckService dbCheckService = new DBCheckService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("text/html;charset=UTF-8");
        
        try {
            DBStatus status = dbCheckService.getStatus();
            if (status == null) {
                throw new SQLException("数据库未返回状态信息");
            }
            
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html>");
            html.append("<head><title>数据库连接测试</title>");
            html.append("<style>body{font-family:Arial;padding:20px;}</style>");
            html.append("</head>");
            html.append("<body>");
            html.append("<h1>✅ 数据库连接成功</h1>");
            html.append("<p><strong>当前时间：</strong>").append(status.getCurrentTime()).append("</p>");
            html.append("<p><strong>数据库名：</strong>").append(status.getDbName()).append("</p>");
            
            html.append("<p><a href=\"/\">返回首页</a></p>");
            html.append("</body>");
            html.append("</html>");
            
            resp.getWriter().write(html.toString());
            logResult(req, true, null);
            
        } catch (SQLException e) {
            resp.getWriter().write("<!DOCTYPE html>");
            resp.getWriter().write("<html><head><title>数据库连接失败</title>");
            resp.getWriter().write("<style>body{font-family:Arial;padding:20px;color:red;}</style>");
            resp.getWriter().write("</head><body>");
            resp.getWriter().write("<h1>❌ 数据库连接失败</h1>");
            resp.getWriter().write("<p>错误信息：" + e.getMessage() + "</p>");
            resp.getWriter().write("<p><a href=\"/\">返回首页</a></p>");
            resp.getWriter().write("</body></html>");
            logResult(req, false, e.getMessage());
            e.printStackTrace();
        }
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("DBCheckServlet " + uri + " " + result);
        } else {
            System.out.println("DBCheckServlet " + uri + " " + result + " - " + message);
        }
    }
}
