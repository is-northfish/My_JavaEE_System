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
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">图书详情</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/cart/list">购物车</a>
        <a href="<%= request.getContextPath() %>/">首页</a>
      </div>
    </div>

    <div class="panel">
      <% if (book == null) { %>
        <p class="notice error">图书不存在</p>
      <% } else { %>
        <p>书名：<strong><%= book.getName() %></strong></p>
        <p>价格：<%= book.getPrice() %></p>
        <p>库存：<%= book.getStock() %></p>
        <p class="muted">分类ID：<%= book.getCategoryId() %></p>
        <form class="form" method="post" action="<%= request.getContextPath() %>/cart/add">
          <input type="hidden" name="bookId" value="<%= book.getId() %>"/>
          <label class="field">数量：
            <input type="number" name="quantity" value="1" min="1"/>
          </label>
          <button class="btn" type="submit">加入购物车</button>
        </form>
      <% } %>
    </div>

    <div class="panel actions">
      <a class="btn secondary" href="<%= request.getContextPath() %>/">返回首页</a>
      <a class="btn ghost" href="<%= request.getContextPath() %>/cart/list">查看购物车</a>
    </div>
  </main>
</body>
</html>
