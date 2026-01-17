<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  Category category = (Category) request.getAttribute("category");
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>编辑分类</title>
</head>
<body>
<h2>编辑分类</h2>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<% if (category == null) { %>
  <p>分类不存在</p>
<% } else { %>
  <form method="post" action="<%= request.getContextPath() %>/admin/category/edit">
    <input type="hidden" name="id" value="<%= category.getId() %>"/>
    <label>分类名称：
      <input type="text" name="name" value="<%= category.getName() %>" required />
    </label>
    <button type="submit">保存</button>
  </form>
<% } %>

<p><a href="<%= request.getContextPath() %>/admin/categories">返回分类列表</a></p>
</body>
</html>
