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
    <title>BORROW BOOK</title>
</head>
<body>
<p>BORROW BOOK</p>

<p style="color: red">${errorMessage}</p>
<form method = "post" >
    YOUR ID : <input type = "text" name="student_id" />
    BOOK ID : <input type = "text" name="book_id" />
    <input type="submit" value ="BORROW"/>
</form>

</body>
</html>
