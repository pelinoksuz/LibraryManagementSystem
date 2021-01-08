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
    <title>List Number of Publishers' Borrowed Books</title>
</head>
<body>
<%
    String[][] data = (String[][]) session.getAttribute("borrowedData");

    if (data != null)
    {
        for (String[] book : data)
        {
%>
<p> PUBLISHER NAME: <%= book[0] %>  BORROWED TIMES : <%= book[1] %></p>
<%
        }
    }
%>
</body>
</html>
