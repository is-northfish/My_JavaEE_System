<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  if (categories == null) {
    categories = java.util.Collections.emptyList();
  }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>分类列表</title>
</head>
<body>
<h2>分类列表</h2>

<p>
  <a href="<%= request.getContextPath() %>/admin/category/add">新增分类</a>
  |
  <a href="<%= request.getContextPath() %>/admin/index.jsp">返回后台</a>
</p>

<% if (categories.isEmpty()) { %>
  <p>暂无分类</p>
<% } else { %>
  <table border="1" cellpadding="6" cellspacing="0">
    <tr>
      <th>ID</th>
      <th>名称</th>
      <th>操作</th>
    </tr>
    <% for (Category category : categories) { %>
      <tr>
        <td><%= category.getId() %></td>
        <td><%= category.getName() %></td>
        <td>
          <a href="<%= request.getContextPath() %>/admin/category_edit.jsp?id=<%= category.getId() %>">编辑</a>
          |
          <form method="post" action="<%= request.getContextPath() %>/admin/category/delete" style="display:inline;">
            <input type="hidden" name="id" value="<%= category.getId() %>"/>
            <button type="submit">删除</button>
          </form>
        </td>
      </tr>
    <% } %>
  </table>
<% } %>
</body>
</html>
