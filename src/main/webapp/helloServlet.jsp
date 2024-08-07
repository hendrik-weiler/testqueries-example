<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hendrikweiler.testqueries.HelloServlet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script>
        var data = <%= (String)request.getAttribute("customers_json") %>;
    </script>
    <script src="helloServlet.js"></script>
</head>
<body>
<table id="table">
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Password</th>
    </tr>
</table>
<div class="options">
    <%
        Object the_page_param = request.getAttribute("page");
        int the_page = 0;
        if(the_page_param != null) {
            the_page = Integer.parseInt(the_page_param.toString());
        }
    %>
    <a href="?page=<%= the_page-1 %>">Previous page</a>
    <a href="?page=<%= the_page+1 %>">Next page</a>
</div>
<form action="" method="post">
    <input type="text" name="username" placeholder="Name">
    <input type="password" name="password" placeholder="PW">
    <input type="submit" value="Submit">
</form>
</body>
</html>