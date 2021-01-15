<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: pelin
  Date: 26.12.2020
  Time: 15:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login to System</title>
</head>
<body>
<%
    java.sql.Date date_start = (java.sql.Date) session.getAttribute("date_start");

    if(date_start == null)
    {
%>
<p style="color: red">${errorMessage}</p>
<form method = "post" >
    STARTING DATE : <input type = "text" name="date_start" />
    ENDING DATE : <input type = "text" name="date_returned"/>
    <input type="submit" value ="SEARCH"/>
</form>
<%
}else{
%>
<p>You can starting listing from: <%= date_start %> </p>
<a href="/listUsersBorrowedCurrently">LIST</a>

<%
    }
%>
</body>
</html>
