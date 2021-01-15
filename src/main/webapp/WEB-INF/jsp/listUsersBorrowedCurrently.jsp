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
    <title>listUsersBorrowedCurrently</title>
</head>
<body>
<%
    String[][] data = (String[][]) session.getAttribute("userBorrowedData");

    if (data != null)
    {
        for (String[] book : data)
        {
%>
<p>  STUDENT NAME: <%=book[0] %> :  STUDENT SURNAME: <%= book[1] %>: TITLE: <%= book[2] %>: AUTHOR NAME: <%= book[3] %>: BORROWED DATE:<%= book[4] %> DELIVERY DATE:<%= book[5] %>   </p>
<%
        }
    }

%>
</body>
</html>
