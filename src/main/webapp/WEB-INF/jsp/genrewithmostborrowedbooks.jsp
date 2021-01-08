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
    <title>genre with most borrowed booked Books</title>
</head>
<body>
<%
    String[][] data = (String[][]) session.getAttribute("mostBorrowedData");

    if (data != null)
    {
        for (String[] book : data)
        {
%>
<p>GENRE: <%= book[0] %>    BORROWED TIMES: <%= book[1] %> </p>
<%
        }
    }
%>
</body>
</html>
