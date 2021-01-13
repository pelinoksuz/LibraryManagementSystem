<%--
  Created by IntelliJ IDEA.
  User: pelin
  Date: 26.12.2020
  Time: 15:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD BOOK</title>
</head>
<body>
<%
    String title = (String) session.getAttribute("title");

    if(title == null)
    {
%>
<p style="color: red">${errorMessage}</p>
<form method = "post" >
    genre : <input type = "text" name="genre" />
    author name : <input type = "text" name="author_name" />
    title : <input type = "text" name="title" />
    status : <input type = "text" name="status" />
    Publication Date : <input type = "text" name="publication_date" />
    Borrowed time : <input type = "text" name="times_borrowed" />
    Requested/ Not Requested : <input type = "text" name="requested" />
    Publisher ID : <input type = "text" name="pub_id" />
    Librarian ID : <input type = "text" name="lib_id" />
    <input type="submit" value ="ADD"/>
</form>
<%
}else{
%>
<p>Book was added as: <%= title %> </p>

<%
    }
%>
</body>
</html>
