<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%@ page import="com.example.bookmall.entity.Book" %>
<%
  String username = (String) session.getAttribute("username");
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  Map<Long, List<Book>> booksByCategory = (Map<Long, List<Book>>) request.getAttribute("booksByCategory");
  if (categories == null) {
    categories = java.util.Collections.emptyList();
  }
  if (booksByCategory == null) {
    booksByCategory = java.util.Collections.emptyMap();
  }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>首页</title>
</head>
<body>
<h2>图书商城</h2>

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

<h3>分类与图书</h3>
<% if (categories.isEmpty()) { %>
  <p>暂无分类</p>
<% } else { %>
  <% for (Category category : categories) { %>
    <h4><a href="books?categoryId=<%= category.getId() %>"><%= category.getName() %></a></h4>
    <%
      List<Book> books = booksByCategory.get(category.getId());
      if (books == null || books.isEmpty()) {
    %>
      <p>暂无图书</p>
    <%
      } else {
    %>
      <ul>
        <% for (Book book : books) { %>
          <li>
            <a href="book?id=<%= book.getId() %>"><%= book.getName() %></a>
            - <%= book.getPrice() %>
          </li>
        <% } %>
      </ul>
    <%
      }
    %>
  <% } %>
<% } %>

</body>
</html>
