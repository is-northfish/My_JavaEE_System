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
    <header class="header">
      <div class="brand">✏️ 编辑用户</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/admin/users">返回列表</a>
      </nav>
    </header>

    <div class="grid two">
      <div></div>
      <div>
        <% if (error != null && !error.isEmpty()) { %>
          <div class="notice error"><%= error %></div>
        <% } %>

        <div class="panel">
          <% if (user == null) { %>
            <div class="notice error">😕 用户不存在</div>
            <div style="text-align: center; margin-top: 20px;">
              <a class="btn secondary" href="<%= request.getContextPath() %>/admin/users">返回列表</a>
            </div>
          <% } else { %>
            <form class="form" method="post" action="<%= request.getContextPath() %>/admin/user/edit">
              <input type="hidden" name="id" value="<%= user.getId() %>"/>
              <div style="background: #f0e8dd; padding: 12px; border-radius: 8px; margin-bottom: 20px;">
                <p style="margin: 0; font-size: 12px; color: var(--muted);">用户 ID</p>
                <p style="margin: 4px 0 0 0; font-weight: 600; font-size: 18px;"><%= user.getId() %></p>
              </div>
              <div class="field">
                <label for="username">用户名</label>
                <input id="username" type="text" name="username" value="<%= user.getUsername() %>" required />
              </div>
              <div class="field">
                <label for="password">新密码</label>
                <input id="password" type="password" name="password" placeholder="留空以保持原密码" />
              </div>
              <button class="btn" type="submit">✓ 保存更改</button>
            </form>
          <% } %>
        </div>

        <% if (user != null) { %>
          <div class="panel actions">
            <a class="btn ghost" href="<%= request.getContextPath() %>/admin/users">取消</a>
          </div>
        <% } %>
      </div>
      <div></div>
    </div>
  </main>
</body>
</html>
