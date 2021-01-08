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
    String pub_username = (String) session.getAttribute("pub_username");

    if(pub_username == null)
    {
%>
<p style="color: red">${errorMessage}</p>
<form method = "post" >
    Username : <input type = "text" name="pub_username" />
    Password : <input type = "password" name="pub_password"/>
    <input type="submit" value ="Login"/>
</form>
<%
}else{
%>
<p>You are logged in as: <%= pub_username %> </p>
<a href="/informationOfBooks">Information Of Books</a>
<a href="/countsOfBorrowing">Counts of Borrowing</a>
<a href="/logout">Logout</a>
<%
    }
%>
</body>
</html>
