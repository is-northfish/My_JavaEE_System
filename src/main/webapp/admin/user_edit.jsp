<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.bookmall.entity.User" %>
<%
  User user = (User) request.getAttribute("user");
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>编辑用户</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">编辑用户</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/admin/users">用户列表</a>
      </div>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (user == null) { %>
        <p class="notice error">用户不存在</p>
      <% } else { %>
        <form class="form" method="post" action="<%= request.getContextPath() %>/admin/user/edit">
          <input type="hidden" name="id" value="<%= user.getId() %>"/>
          <label class="field">用户名：
            <input type="text" name="username" value="<%= user.getUsername() %>" required />
          </label>
          <label class="field">新密码：
            <input type="password" name="password" required />
          </label>
          <button class="btn" type="submit">保存</button>
        </form>
      <% } %>
    </div>

    <div class="panel actions">
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/users">返回用户列表</a>
    </div>
  </main>
</body>
</html>
