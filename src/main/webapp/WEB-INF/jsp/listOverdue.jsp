<%--
  Created by IntelliJ IDEA.
  User: pelin
  Date: 1/6/2021
  Time: 6:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Overdue Books</title>
</head>
<body>
<%
    String[][] data = (String[][]) session.getAttribute("overdueData");

    if (data != null)
    {
        for (String[] book : data)
        {
%>
<p>BOOKS TO BE DELIVERED ARE LISTED: </p>
<p> ST ID: <%= book[0] %> ST NAME : <%= book[1] %> ST SURNAME : <%= book[2] %> BOOK ID : <%= book[3] %> BOOK NAME : <%= book[4] %> AUTHOR : <%= book[5] %></p>
<%
        }
    }
%>
</body>
</html>
