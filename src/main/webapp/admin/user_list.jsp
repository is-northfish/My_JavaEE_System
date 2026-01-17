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
    <header class="header">
      <div class="brand">👥 用户管理</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/admin/index.jsp">后台首页</a>
      </nav>
    </header>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel actions">
      <a class="btn" href="<%= request.getContextPath() %>/admin/user/add">➕ 新增用户</a>
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/index.jsp">返回</a>
    </div>

    <div class="panel">
      <% if (users.isEmpty()) { %>
        <div style="text-align: center; padding: 40px 0;">
          <p style="font-size: 48px; margin: 0;">👻</p>
          <p style="color: var(--muted); margin: 12px 0 0 0;">暂无用户</p>
        </div>
      <% } else { %>
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>用户名</th>
              <th>角色</th>
              <th>状态</th>
              <th>创建时间</th>
              <th style="width: 220px;">操作</th>
            </tr>
          </thead>
          <tbody>
            <% for (User user : users) { %>
              <tr>
                <td><span style="background: #f0e8dd; padding: 2px 8px; border-radius: 4px; font-weight: 600; font-size: 12px;"><%= user.getId() %></span></td>
                <td><strong><%= user.getUsername() %></strong></td>
                <td>
                  <% if ("ADMIN".equals(user.getRole())) { %>
                    <span style="background: #f0e8dd; padding: 2px 6px; border-radius: 4px; font-size: 12px; font-weight: 500;">⚙️ <%= user.getRole() %></span>
                  <% } else { %>
                    <span style="background: #f0f8fa; padding: 2px 6px; border-radius: 4px; font-size: 12px; font-weight: 500;">👤 <%= user.getRole() %></span>
                  <% } %>
                </td>
                <td>
                  <% if (user.getStatus() == 1) { %>
                    <span style="color: #52a552; font-weight: 600;">✓ 正常</span>
                  <% } else { %>
                    <span style="color: #a12828; font-weight: 600;">✗ 禁用</span>
                  <% } %>
                </td>
                <td><span style="color: var(--muted); font-size: 12px;"><%= user.getCreatedAt() %></span></td>
                <td class="actions">
                  <% if (user.getStatus() == 1) { %>
                    <form method="post" action="<%= request.getContextPath() %>/admin/user/disable" style="display:inline;">
                      <input type="hidden" name="id" value="<%= user.getId() %>"/>
                      <button class="btn ghost" type="submit">🔒 禁用</button>
                    </form>
                  <% } else { %>
                    <span class="muted" style="display: inline-block; padding: 6px 10px;">已禁用</span>
                  <% } %>
                  <a class="btn secondary" href="<%= request.getContextPath() %>/admin/user/edit?id=<%= user.getId() %>">✏️ 编辑</a>
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
