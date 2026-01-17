package com.example.bookmall.service;

import com.example.bookmall.entity.CartItem;
import com.example.bookmall.entity.Book;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartService {
    public static final String CART_SESSION_KEY = "cart";
    private final BookService bookService = new BookService();

    @SuppressWarnings("unchecked")
    public Map<Long, CartItem> getCart(HttpSession session) {
        Object value = session.getAttribute(CART_SESSION_KEY);
        if (value instanceof Map) {
            return (Map<Long, CartItem>) value;
        }
        Map<Long, CartItem> cart = new LinkedHashMap<>();
        session.setAttribute(CART_SESSION_KEY, cart);
        return cart;
    }

    public String addToCart(HttpSession session, long bookId, int quantity) throws SQLException {
        if (quantity <= 0) {
            return "数量必须大于 0";
        }
        Book book = bookService.findById(bookId);
        if (book == null) {
            return "图书不存在";
        }
        if (quantity > book.getStock()) {
            return "库存不足";
        }

        Map<Long, CartItem> cart = getCart(session);
        CartItem item = cart.get(bookId);
        if (item == null) {
            item = new CartItem();
            item.setBook(book);
            item.setQuantity(quantity);
            cart.put(bookId, item);
        } else {
            int newQuantity = item.getQuantity() + quantity;
            if (newQuantity > book.getStock()) {
                return "库存不足";
            }
            item.setBook(book);
            item.setQuantity(newQuantity);
        }
        return null;
    }

    public String updateQuantity(HttpSession session, long bookId, int quantity) throws SQLException {
        Map<Long, CartItem> cart = getCart(session);
        CartItem item = cart.get(bookId);
        if (item == null) {
            return "购物车中无此图书";
        }
        if (quantity <= 0) {
            cart.remove(bookId);
            return null;
        }

        Book book = bookService.findById(bookId);
        if (book == null) {
            cart.remove(bookId);
            return "图书不存在";
        }
        if (quantity > book.getStock()) {
            return "库存不足";
        }

        item.setBook(book);
        item.setQuantity(quantity);
        return null;
    }

    public void removeItem(HttpSession session, long bookId) {
        Map<Long, CartItem> cart = getCart(session);
        cart.remove(bookId);
    }

    public java.math.BigDecimal calculateTotal(Map<Long, CartItem> cart) {
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        for (CartItem item : cart.values()) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
