<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.User" %>
<%
  List<User> users = (List<User>) request.getAttribute("users");
  if (users == null) {
    users = java.util.Collections.emptyList();
  }
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>用户列表</title>
</head>
<body>
<h2>用户列表</h2>

<p><a href="<%= request.getContextPath() %>/admin/index.jsp">返回后台</a></p>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<% if (users.isEmpty()) { %>
  <p>暂无用户</p>
<% } else { %>
  <table border="1" cellpadding="6" cellspacing="0">
    <tr>
      <th>ID</th>
      <th>用户名</th>
      <th>角色</th>
      <th>状态</th>
      <th>创建时间</th>
      <th>操作</th>
    </tr>
    <% for (User user : users) { %>
      <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getUsername() %></td>
        <td><%= user.getRole() %></td>
        <td><%= user.getStatus() == 1 ? "正常" : "禁用" %></td>
        <td><%= user.getCreatedAt() %></td>
        <td>
          <% if (user.getStatus() == 1) { %>
            <form method="post" action="<%= request.getContextPath() %>/admin/user/disable" style="display:inline;">
              <input type="hidden" name="id" value="<%= user.getId() %>"/>
              <button type="submit">禁用</button>
            </form>
          <% } else { %>
            已禁用
          <% } %>
        </td>
      </tr>
    <% } %>
  </table>
<% } %>
</body>
</html>
