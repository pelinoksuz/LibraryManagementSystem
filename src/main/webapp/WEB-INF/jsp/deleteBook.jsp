<%--
  Created by IntelliJ IDEA.
  User: pelin
  Date: 1/4/2021
  Time: 8:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DELETE BOOK</title>
</head>
<body>
<p>DELETE BOOK</p>


<%
    String name = (String) session.getAttribute("book_id");

    if(name == null)
    {
%>
<p style="color: red">${errorMessage}</p>
<form method = "post" >
    BOOK ID : <input type = "text" name="book_id" />
    <input type="submit" value ="DELETE"/>
</form>
<%
}else{
%>
<p>Book deleted successfully! </p>
<%
    }
%>
</body>
</html>
