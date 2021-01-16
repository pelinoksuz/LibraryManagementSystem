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
    <title>BORROW HISTORY</title>
</head>
<body>
<%
    String[][] borrowHistData = (String[][]) session.getAttribute("borrowHistData");

    if (borrowHistData != null)
    {
    for (String[] book : borrowHistData)
    {
%>
<p>  NAME:<%= book[0] %> : SURNAME: <%= book[1] %>: TITLE: <%= book[2] %>: AUTHOR_NAME: <%= book[3] %>: DATE START: <%= book[4] %> :DATE DELIVERY: <%= book[5] %>   </p>
<%
    }
    }
%>
</body>
</html>
