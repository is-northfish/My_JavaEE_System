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
    <div class="header">
      <div class="brand">图书商城</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/cart/list">购物车</a>
        <a href="<%= request.getContextPath() %>/admin/index.jsp">后台</a>
      </div>
    </div>

    <div class="panel">
      <% if (username != null && !username.isEmpty()) { %>
        <p>你好！<strong><%= username %></strong></p>
        <form method="post" action="logout">
          <button class="btn" type="submit">退出登录</button>
        </form>
      <% } else { %>
        <p class="muted">未登录</p>
        <div class="actions">
          <a class="btn" href="login.jsp">去登录</a>
          <a class="btn secondary" href="register.jsp">去注册</a>
        </div>
      <% } %>
    </div>

    <div class="panel">
      <div class="section-title">分类与图书</div>
      <% if (categories.isEmpty()) { %>
        <p class="muted">暂无分类</p>
      <% } else { %>
        <% for (Category category : categories) { %>
          <div class="panel">
            <h3><a href="books?categoryId=<%= category.getId() %>"><%= category.getName() %></a></h3>
            <%
              List<Book> books = booksByCategory.get(category.getId());
              if (books == null || books.isEmpty()) {
            %>
              <p class="muted">暂无图书</p>
            <%
              } else {
            %>
              <ul>
                <% for (Book book : books) { %>
                  <li>
                    <a href="book?id=<%= book.getId() %>"><%= book.getName() %></a>
                    - <%= book.getPrice() %>
                  </li>
                <% } %>
              </ul>
            <%
              }
            %>
          </div>
        <% } %>
      <% } %>
    </div>
  </main>
</body>
</html>
