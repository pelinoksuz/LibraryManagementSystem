<%--
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
    String lib_username = (String) session.getAttribute("lib_username");

    if(lib_username == null)
    {
%>
<p style="color: red">${errorMessage}</p>
<form method = "post" >
    Username : <input type = "text" name="lib_username" />
    Password : <input type = "password" name="lib_password"/>
    <input type="submit" value ="Login"/>
</form>
<%
}else{
%>
<p>You are logged in as: <%= lib_username %> </p>
<a href="/PublisherSignUp">Create Publisher Account</a>
<a href="/informationOfBooks">Information Of Books</a>
<a href="/logout">Display borrowed books by users</a>
<a href="/logout">Logout</a>
<%
    }
%>
</body>
</html>
