<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Book" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  Book book = (Book) request.getAttribute("book");
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
  <title>编辑图书</title>
</head>
<body>
<h2>编辑图书</h2>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<% if (book == null) { %>
  <p>图书不存在</p>
<% } else { %>
  <form method="post" action="<%= request.getContextPath() %>/admin/book/edit">
    <input type="hidden" name="id" value="<%= book.getId() %>"/>
    <label>分类：
      <select name="categoryId" required>
        <option value="">请选择</option>
        <% for (Category category : categories) { %>
          <option value="<%= category.getId() %>" <%= category.getId() == book.getCategoryId() ? "selected" : "" %>>
            <%= category.getName() %>
          </option>
        <% } %>
      </select>
    </label>
    <br/>
    <label>书名：
      <input type="text" name="name" value="<%= book.getName() %>" required />
    </label>
    <br/>
    <label>价格：
      <input type="text" name="price" value="<%= book.getPrice() %>" required />
    </label>
    <br/>
    <label>库存：
      <input type="number" name="stock" value="<%= book.getStock() %>" min="0" required />
    </label>
    <br/>
    <button type="submit">保存</button>
  </form>
<% } %>

<p><a href="<%= request.getContextPath() %>/admin/books">返回图书列表</a></p>
</body>
</html>
