<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bookmall.entity.Category" %>
<%
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  if (categories == null) {
    categories = java.util.Collections.emptyList();
  }
  String error = (String) request.getAttribute("error");
  String name = (String) request.getAttribute("name");
  String price = (String) request.getAttribute("price");
  String stock = (String) request.getAttribute("stock");
  String categoryId = (String) request.getAttribute("categoryId");
  if (name == null) { name = ""; }
  if (price == null) { price = ""; }
  if (stock == null) { stock = ""; }
  if (categoryId == null) { categoryId = ""; }
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>新增图书</title>
</head>
<body>
<h2>新增图书</h2>

<% if (error != null && !error.isEmpty()) { %>
  <p style="color:red;"><%= error %></p>
<% } %>

<form method="post" action="<%= request.getContextPath() %>/admin/book/add">
  <label>分类：
    <select name="categoryId" required>
      <option value="">请选择</option>
      <% for (Category category : categories) { %>
        <option value="<%= category.getId() %>" <%= String.valueOf(category.getId()).equals(categoryId) ? "selected" : "" %>>
          <%= category.getName() %>
        </option>
      <% } %>
    </select>
  </label>
  <br/>
  <label>书名：
    <input type="text" name="name" value="<%= name %>" required />
  </label>
  <br/>
  <label>价格：
    <input type="text" name="price" value="<%= price %>" required />
  </label>
  <br/>
  <label>库存：
    <input type="number" name="stock" value="<%= stock %>" min="0" required />
  </label>
  <br/>
  <button type="submit">提交</button>
</form>

<p><a href="<%= request.getContextPath() %>/admin/books">返回图书列表</a></p>
</body>
</html>
