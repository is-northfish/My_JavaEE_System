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
    <header class="header">
      <div class="brand">📖 图书详情</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/cart/list">🛒 购物车</a>
        <a href="<%= request.getContextPath() %>/">首页</a>
      </nav>
    </header>

    <div class="grid two">
      <div></div>
      <div class="panel">
        <% if (book == null) { %>
          <div class="notice error">😕 图书不存在</div>
          <div style="text-align: center; margin-top: 20px;">
            <a class="btn secondary" href="<%= request.getContextPath() %>/">返回首页</a>
          </div>
        <% } else { %>
          <div style="margin-bottom: 28px;">
            <h2 style="margin: 0 0 12px 0; font-size: 24px;"><%= book.getName() %></h2>
            <p style="margin: 0; color: var(--muted); font-size: 14px;">分类 ID: <%= book.getCategoryId() %></p>
          </div>

          <div style="padding: 20px; background: #f9f7f2; border-radius: 8px; margin-bottom: 20px;">
            <div style="font-size: 12px; color: var(--muted); margin-bottom: 6px;">价格</div>
            <div style="font-size: 32px; font-weight: 700; color: var(--accent);">¥ <%= book.getPrice() %></div>
          </div>

          <div style="padding: 16px; background: #f0f8fa; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid var(--accent-2);">
            <div style="font-size: 12px; color: var(--accent-2); margin-bottom: 4px;">库存状态</div>
            <% if (book.getStock() > 0) { %>
              <div style="font-weight: 600; color: var(--accent-2);">✓ 有货（<%= book.getStock() %> 本）</div>
            <% } else { %>
              <div style="font-weight: 600; color: #a12828;">✗ 缺货</div>
            <% } %>
          </div>

          <% if (book.getStock() > 0) { %>
            <form class="form" method="post" action="<%= request.getContextPath() %>/cart/add">
              <input type="hidden" name="bookId" value="<%= book.getId() %>"/>
              <div class="field">
                <label for="quantity">购买数量</label>
                <input id="quantity" type="number" name="quantity" value="1" min="1" max="<%= book.getStock() %>" />
              </div>
              <button class="btn" type="submit">🛒 加入购物车</button>
            </form>
          <% } %>
        <% } %>
      </div>
      <div></div>
    </div>

    <div class="panel actions">
      <% if (book != null && book.getStock() > 0) { %>
        <a class="btn secondary" href="<%= request.getContextPath() %>/">继续浏览</a>
        <a class="btn ghost" href="<%= request.getContextPath() %>/cart/list">查看购物车</a>
      <% } else { %>
        <a class="btn secondary" href="<%= request.getContextPath() %>/">返回首页</a>
      <% } %>
    </div>
  </main>
</body>
</html>
