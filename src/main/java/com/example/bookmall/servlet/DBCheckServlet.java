package com.example.bookmall.servlet;

import com.example.bookmall.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接测试页面
 * 访问 /dbCheck 可以验证数据库连接是否正常
 */
@WebServlet("/dbCheck")
public class DBCheckServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("text/html;charset=UTF-8");
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            
            // 执行简单查询
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT NOW() as current_time, DATABASE() as db_name");
            
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html>");
            html.append("<head><title>数据库连接测试</title>");
            html.append("<style>body{font-family:Arial;padding:20px;}</style>");
            html.append("</head>");
            html.append("<body>");
            html.append("<h1>✅ 数据库连接成功</h1>");
            
            if (rs.next()) {
                html.append("<p><strong>当前时间：</strong>").append(rs.getString("current_time")).append("</p>");
                html.append("<p><strong>数据库名：</strong>").append(rs.getString("db_name")).append("</p>");
            }
            
            html.append("<p><a href=\"/\">返回首页</a></p>");
            html.append("</body>");
            html.append("</html>");
            
            resp.getWriter().write(html.toString());
            
        } catch (SQLException e) {
            resp.getWriter().write("<!DOCTYPE html>");
            resp.getWriter().write("<html><head><title>数据库连接失败</title>");
            resp.getWriter().write("<style>body{font-family:Arial;padding:20px;color:red;}</style>");
            resp.getWriter().write("</head><body>");
            resp.getWriter().write("<h1>❌ 数据库连接失败</h1>");
            resp.getWriter().write("<p>错误信息：" + e.getMessage() + "</p>");
            resp.getWriter().write("<p><a href=\"/\">返回首页</a></p>");
            resp.getWriter().write("</body></html>");
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            DBUtil.close(conn);
        }
    }
}
