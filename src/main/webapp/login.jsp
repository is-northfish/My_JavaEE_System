<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String error = (String) request.getAttribute("error");
  String username = (String) request.getAttribute("username");
  if (username == null) {
    username = "";
  }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>登录</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <header class="header">
      <div class="brand">🔐 用户登录</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/">返回首页</a>
      </nav>
    </header>

    <div class="grid two">
      <div></div>
      <div class="panel">
        <% if (error != null && !error.isEmpty()) { %>
          <div class="notice error"><%= error %></div>
        <% } %>

        <form class="form" method="post" action="login">
          <div class="field">
            <label for="username">用户名</label>
            <input id="username" type="text" name="username" value="<%= username %>" required />
          </div>
          <div class="field">
            <label for="password">密码</label>
            <input id="password" type="password" name="password" required />
          </div>
          <button class="btn" type="submit">登录</button>
        </form>

        <hr style="border: none; border-top: 1px solid var(--border); margin: 20px 0;">
        <p style="text-align: center; color: var(--muted); margin: 0;">还没有账号？</p>
        <div style="text-align: center; margin-top: 12px;">
          <a class="btn secondary" href="register.jsp">去注册新账号</a>
        </div>
      </div>
      <div></div>
    </div>
  </main>
</body>
</html>
