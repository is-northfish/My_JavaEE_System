<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Book" %>
<%
  List<Book> books = (List<Book>) request.getAttribute("books");
  if (books == null) {
    books = java.util.Collections.emptyList();
  }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>图书列表</title>
</head>
<body>
<h2>图书列表</h2>

<p>
  <a href="<%= request.getContextPath() %>/admin/book/add">新增图书</a>
  |
  <a href="<%= request.getContextPath() %>/admin/index.jsp">返回后台</a>
</p>

<% if (books.isEmpty()) { %>
  <p>暂无图书</p>
<% } else { %>
  <table border="1" cellpadding="6" cellspacing="0">
    <tr>
      <th>ID</th>
      <th>分类ID</th>
      <th>书名</th>
      <th>价格</th>
      <th>库存</th>
      <th>操作</th>
    </tr>
    <% for (Book book : books) { %>
      <tr>
        <td><%= book.getId() %></td>
        <td><%= book.getCategoryId() %></td>
        <td><%= book.getName() %></td>
        <td><%= book.getPrice() %></td>
        <td><%= book.getStock() %></td>
        <td>
          <a href="<%= request.getContextPath() %>/admin/book/edit?id=<%= book.getId() %>">编辑</a>
          |
          <form method="post" action="<%= request.getContextPath() %>/admin/book/delete" style="display:inline;">
            <input type="hidden" name="id" value="<%= book.getId() %>"/>
            <button type="submit">删除</button>
          </form>
        </td>
      </tr>
    <% } %>
  </table>
<% } %>
</body>
</html>
