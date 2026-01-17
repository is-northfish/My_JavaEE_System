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
  <title>注册</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">用户注册</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/">首页</a>
      </div>
    </div>

    <div class="panel">
      <% if (error != null && !error.isEmpty()) { %>
        <div class="notice error"><%= error %></div>
      <% } %>

      <form class="form" method="post" action="register">
        <label class="field">用户名：
          <input type="text" name="username" value="<%= username %>" required />
        </label>
        <label class="field">密码：
          <input type="password" name="password" required />
        </label>
        <label class="field">确认密码：
          <input type="password" name="confirmPassword" required />
        </label>
        <button class="btn" type="submit">注册</button>
      </form>
    </div>

    <div class="panel">
      <a class="btn secondary" href="login.jsp">已有账号？去登录</a>
    </div>
  </main>
</body>
</html>
