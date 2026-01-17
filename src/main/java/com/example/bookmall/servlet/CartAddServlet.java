package com.example.bookmall.servlet;

import com.example.bookmall.entity.CartItem;
import com.example.bookmall.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/cart/add")
public class CartAddServlet extends HttpServlet {
    private final CartService cartService = new CartService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String bookIdParam = req.getParameter("bookId");
        String quantityParam = req.getParameter("quantity");
        long bookId;
        int quantity = 1;

        try {
            bookId = Long.parseLong(bookIdParam);
        } catch (NumberFormatException e) {
            writeBadRequest(resp, "图书参数不合法");
            logResult(req, false, "invalid_book");
            return;
        }

        if (quantityParam != null && !quantityParam.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityParam);
            } catch (NumberFormatException e) {
                writeBadRequest(resp, "数量参数不合法");
                logResult(req, false, "invalid_quantity");
                return;
            }
        }

        try {
            HttpSession session = req.getSession();
            String error = cartService.addToCart(session, bookId, quantity);
            if (error != null) {
                forwardWithError(req, resp, error);
                logResult(req, false, "add_failed");
                return;
            }

            logResult(req, true, "added");
            resp.sendRedirect(req.getContextPath() + "/cart/list");
        } catch (SQLException e) {
            writeServerError(resp);
            logResult(req, false, "sql_error");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Long, CartItem> cart = cartService.getCart(session);
        BigDecimal total = cartService.calculateTotal(cart);

        req.setAttribute("cart", cart);
        req.setAttribute("total", total);
        req.setAttribute("error", error);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    private void writeBadRequest(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(message);
    }

    private void writeServerError(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("加入购物车失败");
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("CartAddServlet " + uri + " " + result);
        } else {
            System.out.println("CartAddServlet " + uri + " " + result + " - " + message);
        }
    }
}
