package com.Dao;

import javax.sql.DataSource;

import com.model.Course;
import com.model.StudentCourse;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class StudentDao {

    private final String SERVER_NAME="localhost";
    private final String DATABASE_NAME = "webtask";
    private final String USER_NAME = "root";
    private final String PASSWORD = "1234";
    private final int PORT  = 4000;

    private DataSource ds;

    public StudentDao(){
        try{
            ds = getDataSource();
        }catch (Exception e){

        }
    }
    private  DataSource getDataSource() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(SERVER_NAME);
        ds.setDatabaseName(DATABASE_NAME);
        ds.setUser(USER_NAME);
        ds.setPassword(PASSWORD);
        ds.setPort(PORT);
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);

        return ds;
    }

    private List<StudentCourse> getStudentsCourses(String studentID ) throws SQLException {
        try ( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT student_name ,course_name , courseID , grade FROM studentCourse WHERE studentID="+studentID);
            List<StudentCourse> studentCourses = new LinkedList<>();
            while (rs.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentID(studentID);
                studentCourse.setStudentName(rs.getString("student_name"));
                studentCourse.setCourseID(String.valueOf(rs.getInt("courseID")));
                studentCourse.setCourseName(rs.getString("course_name"));
                studentCourse.setGrade(rs.getInt("grade"));
                studentCourses.add(studentCourse);
            }
            return studentCourses;
        }
    }

    public StudentCourse getStudentCourse(String courseName , String studentID) throws SQLException {
        List<StudentCourse> studentCourses = getStudentsCourses(studentID);
        for (StudentCourse s : studentCourses){
            if (s.getCourseName().equals(courseName))
                return s;
        }
     return null;
    }

    public List<Course> getCourses(String studentID) throws SQLException {
        try ( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT course_name , courseID FROM studentCourse WHERE studentID="+studentID);
            List<Course> courses = new LinkedList<>();
            while (rs.next()) {
                Course course = new Course();
                course.setCourseID(String.valueOf(rs.getInt("courseID")));
                course.setCourseName(rs.getString("course_name"));
                courses.add(course);
            }
            return courses;
        }
    }

    private List<Integer> getAllGradesOfACourse(String courseID) throws SQLException {
        try ( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT grade FROM studentCourse WHERE courseID="+courseID);
            List<Integer> grades = new LinkedList<>();
            while (rs.next()) {
                grades.add(rs.getInt("grade"));
            }
            return grades;
        }
    }
    public int getMinScore(String courseID) throws SQLException {
        List<Integer> list = getAllGradesOfACourse(courseID);
        int min = Integer.MAX_VALUE;
        for (int i : list)
            if (i<min)
                min=i;
        return min;
    }
    public int getMaxScore(String courseID) throws SQLException {
        List<Integer> list = getAllGradesOfACourse(courseID);
        int max = Integer.MIN_VALUE;
        for (int i : list)
            if (i>max)
                max=i;
        return max;
    }
    public double getAvgScore(String courseID) throws SQLException {
        List<Integer> list = getAllGradesOfACourse(courseID);
        int avg = 0;
        for (int i : list)
            avg+=i;
        return avg/list.size();
    }
    private String getStudentName(String studentID){
        try {
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student  WHERE id="+studentID);
            rs.next();
            return rs.getString("name");
        }
        catch (SQLException sqlException){
            return null;
        }
    }

    public boolean isValidUser(String studentName , String studentID){
        String resolvedName = getStudentName(studentID);
        if (resolvedName==null)
            return false;
        if (resolvedName.equals(studentName))
            return true;
        return false;
    }
}
