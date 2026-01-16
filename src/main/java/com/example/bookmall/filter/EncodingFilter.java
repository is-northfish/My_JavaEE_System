package com.example.bookmall.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编码过滤器 - 统一设置 UTF-8 编码
 * 拦截所有请求，确保请求和响应都使用 UTF-8 编码
 */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化时可以打印日志，但不是必须的
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        
        // 设置响应编码
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // 继续处理请求
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 清理资源（如果需要）
    }
}
