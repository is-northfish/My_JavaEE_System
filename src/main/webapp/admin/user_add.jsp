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
  <title>新增用户</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/app.css"/>
</head>
<body class="app">
  <main class="page">
    <div class="header">
      <div class="brand">新增用户</div>
      <div class="nav">
        <a href="<%= request.getContextPath() %>/admin/users">用户列表</a>
      </div>
    </div>

    <% if (error != null && !error.isEmpty()) { %>
      <div class="notice error"><%= error %></div>
    <% } %>

    <div class="panel">
      <form class="form" method="post" action="<%= request.getContextPath() %>/admin/user/add">
        <label class="field">用户名：
          <input type="text" name="username" value="<%= username %>" required />
        </label>
        <label class="field">密码：
          <input type="password" name="password" required />
        </label>
        <button class="btn" type="submit">提交</button>
      </form>
    </div>

    <div class="panel actions">
      <a class="btn ghost" href="<%= request.getContextPath() %>/admin/users">返回用户列表</a>
    </div>
  </main>
</body>
</html>
