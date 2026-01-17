<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.User" %>
<%
  List<User> users = (List<User>) request.getAttribute("users");
  if (users == null) {
    users = java.util.Collections.emptyList();
  }
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>用户列表</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">用户管理</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/admin/index.jsp">后台首页</a>
      </div>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel actions">
      <a class="btn" href="<%= request.getContextPath() %>/admin/user/add">新增用户</a>
    </div>

    <div class="panel">
      <% if (users.isEmpty()) { %>
        <p class="muted">暂无用户</p>
      <% } else { %>
        <table class="table">
          <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>角色</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
          <% for (User user : users) { %>
            <tr>
              <td><%= user.getId() %></td>
              <td><%= user.getUsername() %></td>
              <td><%= user.getRole() %></td>
              <td><%= user.getStatus() == 1 ? "正常" : "禁用" %></td>
              <td><%= user.getCreatedAt() %></td>
              <td>
                <% if (user.getStatus() == 1) { %>
                  <form method="post" action="<%= request.getContextPath() %>/admin/user/disable" style="display:inline;">
                    <input type="hidden" name="id" value="<%= user.getId() %>"/>
                    <button class="btn ghost" type="submit">禁用</button>
                  </form>
                <% } else { %>
                  <span class="muted">已禁用</span>
                <% } %>
                <a class="btn secondary" href="<%= request.getContextPath() %>/admin/user/edit?id=<%= user.getId() %>">编辑</a>
              </td>
            </tr>
          <% } %>
        </table>
      <% } %>
    </div>
  </main>
</body>
</html>
