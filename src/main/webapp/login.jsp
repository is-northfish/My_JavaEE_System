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
</head>
<body>
<h2>用户登录</h2>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<form method="post" action="login">
  <label>用户名：<input type="text" name="username" value="<%= username %>" required /></label>
  <br/>
  <label>密码：<input type="password" name="password" required /></label>
  <br/>
  <button type="submit">登录</button>
</form>

<p><a href="register.jsp">没有账号？去注册</a></p>
</body>
</html>
