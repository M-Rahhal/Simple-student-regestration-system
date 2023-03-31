<%--
  Created by IntelliJ IDEA.
  User: moham
  Date: 10/6/2022
  Time: 2:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="GET" action="show_result">

  <c:forEach items="${courses}" var="course">
    <span>${course.getCourseName()}</span>
    <input  type="radio" name="value" value="${course.getCourseName()}">
    <br>
  </c:forEach>

  <input type="submit" value="submit">
</form>


</body>
</html>
