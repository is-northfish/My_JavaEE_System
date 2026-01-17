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
</head>
<body>
<h2>购物车</h2>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<% if (cart.isEmpty()) { %>
  <p>购物车为空</p>
<% } else { %>
  <table border="1" cellpadding="6" cellspacing="0">
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
          <form method="post" action="update">
            <input type="hidden" name="bookId" value="<%= item.getBook().getId() %>"/>
            <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="0"/>
            <button type="submit">更新</button>
          </form>
        </td>
        <td><%= item.getTotalPrice() %></td>
        <td>
          <form method="post" action="remove">
            <input type="hidden" name="bookId" value="<%= item.getBook().getId() %>"/>
            <button type="submit">删除</button>
          </form>
        </td>
      </tr>
    <% } %>
  </table>
  <p>总价：<strong><%= total %></strong></p>
<% } %>

<p><a href="<%= request.getContextPath() %>/">返回首页</a></p>
</body>
</html>
