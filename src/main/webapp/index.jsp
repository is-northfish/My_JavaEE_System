<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>App Home</title>
</head>
<body>
<h2>It works ✅</h2>

<p>JSP 直接访问：/</p>

<p>Servlet 转发测试：
  <a href="hello">/hello</a>
</p>

<p>
  msg: <%= request.getAttribute("msg") %>
</p>

</body>
</html>
