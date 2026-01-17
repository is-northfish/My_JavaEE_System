<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String username = (String) session.getAttribute("username");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>后台首页</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">后台管理</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/">前台首页</a>
      </div>
    </div>

    <div class="panel">
      <% if (username != null && !username.isEmpty()) { %>
        <p>当前管理员：<strong><%= username %></strong></p>
      <% } %>
      <div class="actions">
        <a class="btn" href="<%= request.getContextPath() %>/admin/categories">分类管理</a>
        <a class="btn secondary" href="<%= request.getContextPath() %>/admin/books">图书管理</a>
        <a class="btn ghost" href="<%= request.getContextPath() %>/admin/users">用户管理</a>
      </div>
    </div>
  </main>
</body>
</html>
