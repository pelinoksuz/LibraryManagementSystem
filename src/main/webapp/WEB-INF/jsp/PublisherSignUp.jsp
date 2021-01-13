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
    <title>Sign Up Page For Publishers</title>
</head>
<body>
<p>SignUp Page</p>
<p>Create a Publisher Account</p>

<%
    String name = (String) session.getAttribute("name");

    if(name == null)
    {
%>
<p style="color: red">${errorMessage}</p>
<form method = "post" >
    Publisher name : <input type = "text" name="name" />
    Publisher username : <input type = "text" name="pub_username" />
    Publisher password : <input type = "text" name="pub_password"/>
    <input type="submit" value ="ADD"/>
</form>
<%
}else{
%>
<p>Account created successfully! </p>
<%
    }
%>
</body>
</html>
