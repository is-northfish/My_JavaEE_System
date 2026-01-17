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
    <header class="header">
      <div class="brand">📂 分类管理</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/admin/index.jsp">后台首页</a>
      </nav>
    </header>

    <div class="panel actions">
      <a class="btn" href="<%= request.getContextPath() %>/admin/category/add">➕ 新增分类</a>
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/index.jsp">返回</a>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <% if (categories.isEmpty()) { %>
        <div style="text-align: center; padding: 40px 0;">
          <p style="font-size: 48px; margin: 0;">📭</p>
          <p style="color: var(--muted); margin: 12px 0 0 0;">暂无分类</p>
        </div>
      <% } else { %>
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>分类名称</th>
              <th style="width: 200px;">操作</th>
            </tr>
          </thead>
          <tbody>
            <% for (Category category : categories) { %>
              <tr>
                <td><span style="background: #f0e8dd; padding: 2px 8px; border-radius: 4px; font-weight: 600; font-size: 12px;"><%= category.getId() %></span></td>
                <td><strong><%= category.getName() %></strong></td>
                <td class="actions">
                  <a class="btn secondary" href="<%= request.getContextPath() %>/admin/category/edit?id=<%= category.getId() %>">✏️ 编辑</a>
                  <form method="post" action="<%= request.getContextPath() %>/admin/category/delete" style="display:inline;">
                    <input type="hidden" name="id" value="<%= category.getId() %>"/>
                    <button class="btn ghost" type="submit">🗑️ 删除</button>
                  </form>
                </td>
              </tr>
            <% } %>
          </tbody>
        </table>
      <% } %>
    </div>
  </main>
</body>
</html>
