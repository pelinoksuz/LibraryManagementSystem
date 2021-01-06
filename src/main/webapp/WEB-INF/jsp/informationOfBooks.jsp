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
<%
    String[][] data = (String[][]) session.getAttribute("bookData");

    if (data != null)
    {
        for (String[] book : data)
        {
%>
<p> <%= book[0] %> : <%= book[1] %>: <%= book[2] %>: <%= book[3] %>: <%= book[4] %>: <%= book[5] %>  </p>
<%
        }
    }
%>
</body>
</html>
