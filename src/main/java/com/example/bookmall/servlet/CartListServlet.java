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
import java.util.Map;

@WebServlet("/cart/list")
public class CartListServlet extends HttpServlet {
    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Long, CartItem> cart = cartService.getCart(session);
        BigDecimal total = cartService.calculateTotal(cart);

        req.setAttribute("cart", cart);
        req.setAttribute("total", total);

        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        logResult(req, true, null);
    }

    private void logResult(HttpServletRequest req, boolean success, String message) {
        String uri = req.getRequestURI();
        String result = success ? "SUCCESS" : "FAIL";
        if (message == null || message.isEmpty()) {
            System.out.println("CartListServlet " + uri + " " + result);
        } else {
            System.out.println("CartListServlet " + uri + " " + result + " - " + message);
        }
    }
}
