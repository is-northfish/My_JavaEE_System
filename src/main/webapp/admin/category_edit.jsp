<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  Category category = (Category) request.getAttribute("category");
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>编辑分类</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">编辑分类</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/admin/categories">分类列表</a>
      </div>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (category == null) { %>
        <p class="notice error">分类不存在</p>
      <% } else { %>
        <form class="form" method="post" action="<%= request.getContextPath() %>/admin/category/edit">
          <input type="hidden" name="id" value="<%= category.getId() %>"/>
          <label class="field">分类名称：
            <input type="text" name="name" value="<%= category.getName() %>" required />
          </label>
          <button class="btn" type="submit">保存</button>
        </form>
      <% } %>
    </div>

    <div class="panel actions">
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/categories">返回分类列表</a>
    </div>
  </main>
</body>
</html>
