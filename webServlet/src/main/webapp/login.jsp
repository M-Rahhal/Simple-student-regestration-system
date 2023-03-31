<%--
  Created by IntelliJ IDEA.
  User: moham
  Date: 10/6/2022
  Time: 1:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>


<p>${message}</p>
<body>
  <form action="login" method="post">

    Please enter student name
    <input type="text" name="studentName"/><br>

    Please enter student ID
    <input type="text" name="studentID"/>

    <input type="submit" value="submit">

  </form>
</body>

</html>
