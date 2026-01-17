<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Book" %>
<%
  List<Book> books = (List<Book>) request.getAttribute("books");
  if (books == null) {
    books = java.util.Collections.emptyList();
  }
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>图书列表</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">图书管理</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/admin/index.jsp">后台首页</a>
      </div>
    </div>

    <div class="panel actions">
      <a class="btn" href="<%= request.getContextPath() %>/admin/book/add">新增图书</a>
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/index.jsp">返回后台</a>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (books.isEmpty()) { %>
        <p class="muted">暂无图书</p>
      <% } else { %>
        <table class="table">
          <tr>
            <th>ID</th>
            <th>分类ID</th>
            <th>书名</th>
            <th>价格</th>
            <th>库存</th>
            <th>操作</th>
          </tr>
          <% for (Book book : books) { %>
            <tr>
              <td><%= book.getId() %></td>
              <td><%= book.getCategoryId() %></td>
              <td><%= book.getName() %></td>
              <td><%= book.getPrice() %></td>
              <td><%= book.getStock() %></td>
              <td class="actions">
                <a class="btn secondary" href="<%= request.getContextPath() %>/admin/book/edit?id=<%= book.getId() %>">编辑</a>
                <form method="post" action="<%= request.getContextPath() %>/admin/book/delete" style="display:inline;">
                  <input type="hidden" name="id" value="<%= book.getId() %>"/>
                  <button class="btn ghost" type="submit">删除</button>
                </form>
              </td>
            </tr>
          <% } %>
        </table>
      <% } %>
    </div>
  </main>
</body>
</html>
