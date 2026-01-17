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
    <header class="header">
      <div class="brand">➕ 新增用户</div>
      <nav class="nav">
        <a href="<%= request.getContextPath() %>/admin/users">返回列表</a>
      </nav>
    </header>

    <div class="grid two">
      <div></div>
      <div>
        <% if (error != null && !error.isEmpty()) { %>
          <div class="notice error"><%= error %></div>
        <% } %>

        <div class="panel">
          <form class="form" method="post" action="<%= request.getContextPath() %>/admin/user/add">
            <div class="field">
              <label for="username">用户名</label>
              <input id="username" type="text" name="username" value="<%= username %>" placeholder="输入用户名" required />
            </div>
            <div class="field">
              <label for="password">初始密码</label>
              <input id="password" type="password" name="password" placeholder="输入初始密码" required />
            </div>
            <button class="btn" type="submit">✓ 创建用户</button>
          </form>
        </div>

        <div class="panel actions">
          <a class="btn ghost" href="<%= request.getContextPath() %>/admin/users">取消</a>
        </div>
      </div>
      <div></div>
    </div>
  </main>
</body>
</html>
