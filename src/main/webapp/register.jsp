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
</head>
<body>
<h2>用户注册</h2>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<form method="post" action="register">
  <label>用户名：<input type="text" name="username" value="<%= username %>" required /></label>
  <br/>
  <label>密码：<input type="password" name="password" required /></label>
  <br/>
  <label>确认密码：<input type="password" name="confirmPassword" required /></label>
  <br/>
  <button type="submit">注册</button>
</form>

<p><a href="login.jsp">已有账号？去登录</a></p>
</body>
</html>
