<%--
  Created by IntelliJ IDEA.
  User: moham
  Date: 10/6/2022
  Time: 2:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<tr>
  student id  <td>${studentCourse.getStudentID()}</td><br>
  student name  <td>${studentCourse.getStudentName()}</td><br>
  course id  <td>${studentCourse.getCourseID()}</td><br>
  course name  <td>${studentCourse.getCourseName()}</td><br>
  grade  <td>${studentCourse.getGrade()}</td><br>

  min  <td>${min}</td><br>
  max  <td>${max}</td><br>
  avg  <td>${avg}</td><br>


</tr>

</body>
</html>
