package com.example.bookmall.servlet;

import com.example.bookmall.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart/remove")
public class CartRemoveServlet extends HttpServlet {
    private final CartService cartService = new CartService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String bookIdParam = req.getParameter("bookId");
        long bookId;
        try {
            bookId = Long.parseLong(bookIdParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "参数不合法");
            logResult(req, false, "invalid_param");
            return;
        }

        HttpSession session = req.getSession();
        cartService.removeItem(session, bookId);
        logResult(req, true, "removed");
        resp.sendRedirect(req.getContextPath() + "/cart/list");
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
            System.out.println("CartRemoveServlet " + uri + " " + result);
        } else {
            System.out.println("CartRemoveServlet " + uri + " " + result + " - " + message);
        }
    }
}
