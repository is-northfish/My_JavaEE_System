<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%@ page import="com.example.bookmall.entity.Book" %>
<%
  String username = (String) session.getAttribute("username");
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  Map<Long, List<Book>> booksByCategory = (Map<Long, List<Book>>) request.getAttribute("booksByCategory");
  if (categories == null) {
    categories = java.util.Collections.emptyList();
  }
  if (booksByCategory == null) {
    booksByCategory = java.util.Collections.emptyMap();
  }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>首页</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <header class="header">
      <div class="brand">📚 图书商城</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/cart/list">🛒 购物车</a>
        <a href="<%= request.getContextPath() %>/admin/index.jsp">⚙️ 后台</a>
      </nav>
    </header>

    <div class="panel user-panel">
      <% if (username != null && !username.isEmpty()) { %>
        <div class="user-info">
          <div class="user-avatar"><%= username.charAt(0) %></div>
          <div>
            <p style="margin: 0; font-weight: 500;">欢迎，<strong><%= username %></strong></p>
            <p style="margin: 4px 0 0 0; font-size: 12px; color: var(--muted);">已登录</p>
          </div>
        </div>
        <form method="post" action="logout">
          <button class="btn ghost" type="submit">退出登录</button>
        </form>
      <% } else { %>
        <div>
          <p style="margin: 0 0 12px 0; color: var(--muted);">您还未登录</p>
          <div class="actions">
            <a class="btn" href="login.jsp">去登录</a>
            <a class="btn secondary" href="register.jsp">去注册</a>
          </div>
        </div>
      <% } %>
    </div>

    <div class="panel">
      <div class="section-title">📖 图书分类</div>
      <% if (categories.isEmpty()) { %>
        <p class="muted">暂无分类</p>
      <% } else { %>
        <div class="grid">
          <% for (Category category : categories) { %>
            <div class="panel">
              <h3 style="margin: 0 0 12px 0;">
                <a href="books?categoryId=<%= category.getId() %>"><%= category.getName() %></a>
              </h3>
              <%
                List<Book> books = booksByCategory.get(category.getId());
                if (books == null || books.isEmpty()) {
              %>
                <p class="muted">暂无图书</p>
              <%
                } else {
              %>
                <div class="book-list">
                  <% for (Book book : books) { %>
                    <a href="book?id=<%= book.getId() %>" style="text-decoration: none;">
                      <div class="book-item">
                        <div class="book-info">
                          <div class="book-title"><%= book.getName() %></div>
                          <div class="book-price">¥ <%= book.getPrice() %></div>
                        </div>
                        <div style="color: var(--muted); font-size: 12px;">查看详情 →</div>
                      </div>
                    </a>
                  <% } %>
                </div>
              <%
                }
              %>
            </div>
          <% } %>
        </div>
      <% } %>
    </div>
  </main>
</body>
</html>
