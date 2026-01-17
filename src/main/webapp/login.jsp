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
    <div class="header">
      <div class="brand">用户登录</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/">首页</a>
      </div>
    </div>

    <div class="panel">
      <% if (error != null && !error.isEmpty()) { %>
        <div class="notice error"><%= error %></div>
      <% } %>

      <form class="form" method="post" action="login">
        <label class="field">用户名：
          <input type="text" name="username" value="<%= username %>" required />
        </label>
        <label class="field">密码：
          <input type="password" name="password" required />
        </label>
        <button class="btn" type="submit">登录</button>
      </form>
    </div>

    <div class="panel">
      <a class="btn secondary" href="register.jsp">没有账号？去注册</a>
    </div>
  </main>
</body>
</html>
