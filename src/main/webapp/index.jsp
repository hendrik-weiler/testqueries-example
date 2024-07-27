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
    <script src="index.js"></script>
</head>
<body>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <%
        ArrayList<HelloServlet.Customer> list = (ArrayList<HelloServlet.Customer>)request.getAttribute("customers");
        for (int i = 0; i < list.size(); i++) { %>
        <tr>
            <td><%= list.get(i).id %></td>
            <td><%= list.get(i).first_name %></td>
        </tr>
    <% } %>
</table>
<form action="" method="post">
    <input type="text" name="first_name" placeholder="First Name">
    <input type="text" name="last_name" placeholder="Last Name">
    <input type="submit" value="Submit">
</form>
</body>
</html>