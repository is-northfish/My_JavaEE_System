<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  if (categories == null) {
    categories = java.util.Collections.emptyList();
  }
  String error = (String) request.getAttribute("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>分类列表</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">分类管理</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/admin/index.jsp">后台首页</a>
      </div>
    </div>

    <div class="panel actions">
      <a class="btn" href="<%= request.getContextPath() %>/admin/category/add">新增分类</a>
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/index.jsp">返回后台</a>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (categories.isEmpty()) { %>
        <p class="muted">暂无分类</p>
      <% } else { %>
        <table class="table">
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>操作</th>
          </tr>
          <% for (Category category : categories) { %>
            <tr>
              <td><%= category.getId() %></td>
              <td><%= category.getName() %></td>
              <td class="actions">
                <a class="btn secondary" href="<%= request.getContextPath() %>/admin/category/edit?id=<%= category.getId() %>">编辑</a>
                <form method="post" action="<%= request.getContextPath() %>/admin/category/delete" style="display:inline;">
                  <input type="hidden" name="id" value="<%= category.getId() %>"/>
                  <button class="btn ghost" type="submit">删除</button>
                </form>
              </td>
            </tr>
          <% } %>
        </table>
      <% } %>
    </div>
  </main>
</body>
</html>
