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
    <header class="header">
      <div class="brand">✏️ 编辑图书</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/admin/books">返回列表</a>
      </nav>
    </header>

    <div class="grid two">
      <div></div>
      <div>
        <% if (error != null && !error.isEmpty()) { %>
          <div class="notice error"><%= error %></div>
        <% } %>

        <div class="panel">
          <% if (book == null) { %>
            <div class="notice error">😕 图书不存在</div>
            <div style="text-align: center; margin-top: 20px;">
              <a class="btn secondary" href="<%= request.getContextPath() %>/admin/books">返回列表</a>
            </div>
          <% } else { %>
            <form class="form" method="post" action="<%= request.getContextPath() %>/admin/book/edit">
              <input type="hidden" name="id" value="<%= book.getId() %>"/>
              <div style="background: #f0e8dd; padding: 12px; border-radius: 8px; margin-bottom: 20px;">
                <p style="margin: 0; font-size: 12px; color: var(--muted);">图书 ID</p>
                <p style="margin: 4px 0 0 0; font-weight: 600; font-size: 18px;"><%= book.getId() %></p>
              </div>
              <div class="field">
                <label for="categoryId">所属分类</label>
                <select id="categoryId" name="categoryId" required>
                  <option value="">-- 请选择分类 --</option>
                  <% for (Category category : categories) { %>
                    <option value="<%= category.getId() %>" <%= category.getId() == book.getCategoryId() ? "selected" : "" %>>
                      <%= category.getName() %>
                    </option>
                  <% } %>
                </select>
              </div>
              <div class="field">
                <label for="name">书名</label>
                <input id="name" type="text" name="name" value="<%= book.getName() %>" required />
              </div>
              <div class="field">
                <label for="price">价格（元）</label>
                <input id="price" type="text" name="price" value="<%= book.getPrice() %>" required />
              </div>
              <div class="field">
                <label for="stock">库存数量</label>
                <input id="stock" type="number" name="stock" value="<%= book.getStock() %>" min="0" required />
              </div>
              <button class="btn" type="submit">✓ 保存更改</button>
            </form>
          <% } %>
        </div>

        <% if (book != null) { %>
          <div class="panel actions">
            <a class="btn ghost" href="<%= request.getContextPath() %>/admin/books">取消</a>
          </div>
        <% } %>
      </div>
      <div></div>
    </div>
  </main>
</body>
</html>
