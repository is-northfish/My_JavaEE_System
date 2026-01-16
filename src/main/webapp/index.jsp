<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String username = (String) session.getAttribute("username");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>首页</title>
</head>
<body>
<h2>图书商城（占位首页）</h2>

<% if (username != null && !username.isEmpty()) { %>
  <p>你好！<%= username %></p>
  <form method="post" action="logout">
    <button type="submit">退出登录</button>
  </form>
<% } else { %>
  <p>未登录</p>
  <p>
    <a href="login.jsp">去登录</a>
    |
    <a href="register.jsp">去注册</a>
  </p>
<% } %>

</body>
</html>
