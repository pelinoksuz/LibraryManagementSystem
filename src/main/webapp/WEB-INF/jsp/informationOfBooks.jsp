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
    <title>Information of Books</title>
</head>
<body>
<a href="/searchBook">Search Book</a>

<%
    String[][] data = (String[][]) session.getAttribute("bookData");

    if (data != null)
    {
        for (String[] book : data)
        {
%>
<p> BOOK ID:<%= book[0] %> : AUTHOR NAME: <%= book[1] %>: PUBLISHED DATE: <%= book[2] %>: TITLE: <%= book[3] %>: GENRE: <%= book[4] %>   </p>
<%
        }
    }
%>
</body>
</html>
