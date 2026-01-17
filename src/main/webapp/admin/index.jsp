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
    <header class="header">
      <div class="brand">⚙️ 后台管理</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/">返回前台</a>
      </nav>
    </header>

    <div class="panel">
      <% if (username != null && !username.isEmpty()) { %>
        <div class="user-info" style="margin-bottom: 24px;">
          <div class="user-avatar"><%= username.charAt(0) %></div>
          <div>
            <p style="margin: 0; font-weight: 500;">管理员：<strong><%= username %></strong></p>
            <p style="margin: 4px 0 0 0; font-size: 12px; color: var(--muted);">您拥有系统管理权限</p>
          </div>
        </div>
      <% } %>

      <div class="section-title">管理功能</div>
      <div class="grid three">
        <div class="panel" style="text-align: center; padding: 24px;">
          <div style="font-size: 32px; margin-bottom: 12px;">📂</div>
          <h3 style="margin: 0 0 12px 0;">分类管理</h3>
          <p style="margin: 0 0 16px 0; color: var(--muted); font-size: 13px;">管理图书分类</p>
          <a class="btn secondary" href="<%= request.getContextPath() %>/admin/categories">进入</a>
        </div>

        <div class="panel" style="text-align: center; padding: 24px;">
          <div style="font-size: 32px; margin-bottom: 12px;">📚</div>
          <h3 style="margin: 0 0 12px 0;">图书管理</h3>
          <p style="margin: 0 0 16px 0; color: var(--muted); font-size: 13px;">管理商城图书</p>
          <a class="btn secondary" href="<%= request.getContextPath() %>/admin/books">进入</a>
        </div>

        <div class="panel" style="text-align: center; padding: 24px;">
          <div style="font-size: 32px; margin-bottom: 12px;">👥</div>
          <h3 style="margin: 0 0 12px 0;">用户管理</h3>
          <p style="margin: 0 0 16px 0; color: var(--muted); font-size: 13px;">管理系统用户</p>
          <a class="btn secondary" href="<%= request.getContextPath() %>/admin/users">进入</a>
        </div>
      </div>
    </div>
  </main>
</body>
  </main>
</body>
</html>
