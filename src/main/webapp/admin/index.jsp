<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String username = (String) session.getAttribute("username");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>后台首页</title>
</head>
<body>
<h2>后台管理</h2>

<% if (username != null && !username.isEmpty()) { %>
  <p>当前管理员：<%= username %></p>
<% } %>

<ul>
  <li><a href="<%= request.getContextPath() %>/admin/categories">分类管理</a></li>
  <li><a href="<%= request.getContextPath() %>/admin/books">图书管理</a></li>
  <li><a href="<%= request.getContextPath() %>/admin/users">用户管理</a></li>
</ul>

<p><a href="<%= request.getContextPath() %>/">返回首页</a></p>
</body>
</html>
