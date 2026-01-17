<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.bookmall.entity.Book" %>
<%
  Book book = (Book) request.getAttribute("book");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>图书详情</title>
</head>
<body>
<h2>图书详情</h2>

<% if (book == null) { %>
  <p>图书不存在</p>
<% } else { %>
  <p>书名：<strong><%= book.getName() %></strong></p>
  <p>价格：<%= book.getPrice() %></p>
  <p>库存：<%= book.getStock() %></p>
  <p>分类ID：<%= book.getCategoryId() %></p>
  <form method="post" action="<%= request.getContextPath() %>/cart/add">
    <input type="hidden" name="bookId" value="<%= book.getId() %>"/>
    <label>数量：
      <input type="number" name="quantity" value="1" min="1"/>
    </label>
    <button type="submit">加入购物车</button>
  </form>
<% } %>

<p>
  <a href="<%= request.getContextPath() %>/">返回首页</a>
  |
  <a href="<%= request.getContextPath() %>/cart/list">查看购物车</a>
</p>
</body>
</html>
