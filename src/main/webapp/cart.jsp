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
    <div class="header">
      <div class="brand">购物车</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/">首页</a>
        <a href="<%= request.getContextPath() %>/books?categoryId=1">继续逛</a>
      </div>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (cart.isEmpty()) { %>
        <p class="muted">购物车为空</p>
      <% } else { %>
        <table class="table">
          <tr>
            <th>书名</th>
            <th>单价</th>
            <th>数量</th>
            <th>小计</th>
            <th>操作</th>
          </tr>
          <% for (CartItem item : cart.values()) { %>
            <tr>
              <td><%= item.getBook().getName() %></td>
              <td><%= item.getBook().getPrice() %></td>
              <td>
                <form method="post" action="update" class="actions">
                  <input type="hidden" name="bookId" value="<%= item.getBook().getId() %>"/>
                  <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="0"/>
                  <button class="btn secondary" type="submit">更新</button>
                </form>
              </td>
              <td><%= item.getTotalPrice() %></td>
              <td>
                <form method="post" action="remove">
                  <input type="hidden" name="bookId" value="<%= item.getBook().getId() %>"/>
                  <button class="btn ghost" type="submit">删除</button>
                </form>
              </td>
            </tr>
          <% } %>
        </table>
        <p>总价：<strong><%= total %></strong></p>
      <% } %>
    </div>

    <div class="panel actions">
      <a class="btn secondary" href="<%= request.getContextPath() %>/">返回首页</a>
    </div>
  </main>
</body>
</html>
