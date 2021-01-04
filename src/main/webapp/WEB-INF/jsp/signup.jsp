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
    <title>Sign Up Page</title>
</head>
<body>
<p>SignUp Page</p>
<p>For student</p>

<%
    String name = (String) session.getAttribute("name");

    if(name == null)
    {
%>
<p style="color: red">${errorMessage}</p>
<form method = "post" >
Student name : <input type = "text" name="name" />
Student surname : <input type = "text" name="surname"/>
Student username : <input type = "text" name="st_username" />
Student password : <input type = "text" name="st_password"/>
Student address : <input type = "text" name="address" />
Student email : <input type = "text" name="email"/>
<input type="submit" value ="Signup"/>
</form>
<%
}else{
%>
<p>You are logged </p>
<a href="/logout">Logout</a>
<%
    }
%>
</body>
</html>
