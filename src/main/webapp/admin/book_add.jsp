<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  if (categories == null) {
    categories = java.util.Collections.emptyList();
  }
  String error = (String) request.getAttribute("error");
  String name = (String) request.getAttribute("name");
  String price = (String) request.getAttribute("price");
  String stock = (String) request.getAttribute("stock");
  String categoryId = (String) request.getAttribute("categoryId");
  if (name == null) { name = ""; }
  if (price == null) { price = ""; }
  if (stock == null) { stock = ""; }
  if (categoryId == null) { categoryId = ""; }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>新增图书</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <header class="header">
      <div class="brand">➕ 新增图书</div>
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
          <form class="form" method="post" action="<%= request.getContextPath() %>/admin/book/add">
            <div class="field">
              <label for="categoryId">所属分类</label>
              <select id="categoryId" name="categoryId" required>
                <option value="">-- 请选择分类 --</option>
                <% for (Category category : categories) { %>
                  <option value="<%= category.getId() %>" <%= String.valueOf(category.getId()).equals(categoryId) ? "selected" : "" %>>
                    <%= category.getName() %>
                  </option>
                <% } %>
              </select>
            </div>
            <div class="field">
              <label for="name">书名</label>
              <input id="name" type="text" name="name" value="<%= name %>" placeholder="输入图书名称" required />
            </div>
            <div class="field">
              <label for="price">价格（元）</label>
              <input id="price" type="text" name="price" value="<%= price %>" placeholder="0.00" required />
            </div>
            <div class="field">
              <label for="stock">库存数量</label>
              <input id="stock" type="number" name="stock" value="<%= stock %>" placeholder="0" min="0" required />
            </div>
            <button class="btn" type="submit">✓ 添加图书</button>
          </form>
        </div>

        <div class="panel actions">
          <a class="btn ghost" href="<%= request.getContextPath() %>/admin/books">取消</a>
        </div>
      </div>
      <div></div>
    </div>
  </main>
</body>
</html>
