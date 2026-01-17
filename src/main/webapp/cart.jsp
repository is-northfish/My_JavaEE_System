<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.bookmall.entity.CartItem" %>
<%
  Map<Long, CartItem> cart = (Map<Long, CartItem>) request.getAttribute("cart");
  if (cart == null) {
    cart = java.util.Collections.emptyMap();
  }
  String error = (String) request.getAttribute("error");
  java.math.BigDecimal total = (java.math.BigDecimal) request.getAttribute("total");
  if (total == null) {
    total = java.math.BigDecimal.ZERO;
  }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>购物车</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <header class="header">
      <div class="brand">🛒 购物车</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/">首页</a>
        <a href="<%= request.getContextPath() %>/books?categoryId=1">继续购物</a>
      </nav>
    </header>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (cart.isEmpty()) { %>
        <div style="text-align: center; padding: 40px 0;">
          <p style="font-size: 48px; margin: 0;">🛒</p>
          <p style="color: var(--muted); margin: 12px 0 0 0;">您的购物车为空</p>
          <a class="btn" href="<%= request.getContextPath() %>/" style="margin-top: 16px;">继续购物</a>
        </div>
      <% } else { %>
        <table class="table">
          <thead>
            <tr>
              <th>图书名称</th>
              <th>单价</th>
              <th>数量</th>
              <th>小计</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <% for (CartItem item : cart.values()) { %>
              <tr>
                <td><strong><%= item.getBook().getName() %></strong></td>
                <td>¥ <%= item.getBook().getPrice() %></td>
                <td>
                  <form method="post" action="update" class="actions" style="display: flex; gap: 6px; align-items: center;">
                    <input type="hidden" name="bookId" value="<%= item.getBook().getId() %>"/>
                    <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="0" style="width: 60px;"/>
                    <button class="btn secondary" type="submit">✓</button>
                  </form>
                </td>
                <td><strong>¥ <%= item.getTotalPrice() %></strong></td>
                <td>
                  <form method="post" action="remove" style="display: inline;">
                    <input type="hidden" name="bookId" value="<%= item.getBook().getId() %>"/>
                    <button class="btn ghost" type="submit">删除</button>
                  </form>
                </td>
              </tr>
            <% } %>
          </tbody>
        </table>

        <div style="display: flex; justify-content: flex-end; margin-top: 20px; padding-top: 20px; border-top: 2px solid var(--border);">
          <div style="text-align: right;">
            <p style="color: var(--muted); margin: 0 0 8px 0;">订单总额：</p>
            <p style="font-size: 28px; font-weight: 700; color: var(--accent); margin: 0;">¥ <%= total %></p>
          </div>
        </div>
      <% } %>
    </div>

    <div class="panel actions">
      <a class="btn secondary" href="<%= request.getContextPath() %>/">继续购物</a>
      <% if (!cart.isEmpty()) { %>
        <a class="btn" href="<%= request.getContextPath() %>/checkout">去结算</a>
      <% } %>
    </div>
  </main>
</body>
</html>
