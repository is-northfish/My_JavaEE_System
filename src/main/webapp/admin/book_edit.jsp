<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Book" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  Book book = (Book) request.getAttribute("book");
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  if (categories == null) {
    categories = java.util.Collections.emptyList();
  }
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>编辑图书</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">编辑图书</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/admin/books">图书列表</a>
      </div>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (book == null) { %>
        <p class="notice error">图书不存在</p>
      <% } else { %>
        <form class="form" method="post" action="<%= request.getContextPath() %>/admin/book/edit">
          <input type="hidden" name="id" value="<%= book.getId() %>"/>
          <label class="field">分类：
            <select name="categoryId" required>
              <option value="">请选择</option>
              <% for (Category category : categories) { %>
                <option value="<%= category.getId() %>" <%= category.getId() == book.getCategoryId() ? "selected" : "" %>>
                  <%= category.getName() %>
                </option>
              <% } %>
            </select>
          </label>
          <label class="field">书名：
            <input type="text" name="name" value="<%= book.getName() %>" required />
          </label>
          <label class="field">价格：
            <input type="text" name="price" value="<%= book.getPrice() %>" required />
          </label>
          <label class="field">库存：
            <input type="number" name="stock" value="<%= book.getStock() %>" min="0" required />
          </label>
          <button class="btn" type="submit">保存</button>
        </form>
      <% } %>
    </div>

    <div class="panel actions">
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/books">返回图书列表</a>
    </div>
  </main>
</body>
</html>
