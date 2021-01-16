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
    String student_id = (String) session.getAttribute("student_id");

    if(student_id == null)
    {
%>
<form method = "post" >
    USER ID : <input type = "text" name="student_id" />
    <input type="submit" value ="ENTER"/>
</form>
<%
    }
%>
</body>
</html>
