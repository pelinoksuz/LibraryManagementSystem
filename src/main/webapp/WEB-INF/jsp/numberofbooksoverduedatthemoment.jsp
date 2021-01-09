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
    <title>Number of Books Overdued at The Moment</title>
</head>
<body>
<%
    String[][] data = (String[][]) session.getAttribute("sumofoverdued");

    if (data != null)
    {
        for (String[] book : data)
        {
%>
<p>sum of overdued book: <%= book[0] %> </p>
<%
        }
    }
%>
</body>
</html>
