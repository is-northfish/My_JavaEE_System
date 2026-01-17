<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String error = (String) request.getAttribute("error");
  String name = (String) request.getAttribute("name");
  if (name == null) {
    name = "";
  }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>新增分类</title>
</head>
<body>
<h2>新增分类</h2>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<form method="post" action="<%= request.getContextPath() %>/admin/category/add">
  <label>分类名称：
    <input type="text" name="name" value="<%= name %>" required />
  </label>
  <button type="submit">提交</button>
</form>

<p><a href="<%= request.getContextPath() %>/admin/categories">返回分类列表</a></p>
</body>
</html>
