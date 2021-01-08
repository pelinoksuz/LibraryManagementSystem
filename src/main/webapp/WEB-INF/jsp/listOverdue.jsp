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
<p>ST ID: - ST NAME - ST SURNAME - BOOK ID - BOOK NAME - PENALTY INFO - AUTHOR</p>
<p> <%= book[0] %> : <%= book[1] %>: <%= book[2] %>: <%= book[3] %>: <%= book[4] %>: <%= book[5] %>: <%= book[6] %></p>
<%
        }
    }
%>
</body>
</html>
