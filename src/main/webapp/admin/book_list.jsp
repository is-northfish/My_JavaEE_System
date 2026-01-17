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
    <header class="header">
      <div class="brand">📚 图书管理</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/admin/index.jsp">后台首页</a>
      </nav>
    </header>

    <div class="panel actions">
      <a class="btn" href="<%= request.getContextPath() %>/admin/book/add">➕ 新增图书</a>
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/index.jsp">返回</a>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (books.isEmpty()) { %>
        <div style="text-align: center; padding: 40px 0;">
          <p style="font-size: 48px; margin: 0;">📭</p>
          <p style="color: var(--muted); margin: 12px 0 0 0;">暂无图书</p>
        </div>
      <% } else { %>
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>分类</th>
              <th>书名</th>
              <th>价格</th>
              <th>库存</th>
              <th style="width: 200px;">操作</th>
            </tr>
          </thead>
          <tbody>
            <% for (Book book : books) { %>
              <tr>
                <td><span style="background: #f0e8dd; padding: 2px 8px; border-radius: 4px; font-weight: 600; font-size: 12px;"><%= book.getId() %></span></td>
                <td><span style="font-size: 12px; color: var(--muted);">分类 <%= book.getCategoryId() %></span></td>
                <td><strong><%= book.getName() %></strong></td>
                <td><strong style="color: var(--accent);">¥ <%= book.getPrice() %></strong></td>
                <td>
                  <% if (book.getStock() > 0) { %>
                    <span style="color: #52a552; font-weight: 600;">✓ <%= book.getStock() %></span>
                  <% } else { %>
                    <span style="color: #a12828; font-weight: 600;">✗ 无</span>
                  <% } %>
                </td>
                <td class="actions">
                  <a class="btn secondary" href="<%= request.getContextPath() %>/admin/book/edit?id=<%= book.getId() %>">✏️ 编辑</a>
                  <form method="post" action="<%= request.getContextPath() %>/admin/book/delete" style="display:inline;">
                    <input type="hidden" name="id" value="<%= book.getId() %>"/>
                    <button class="btn ghost" type="submit">🗑️ 删除</button>
                  </form>
                </td>
              </tr>
            <% } %>
          </tbody>
        </table>
      <% } %>
    </div>
  </main>
</body>
</html>
