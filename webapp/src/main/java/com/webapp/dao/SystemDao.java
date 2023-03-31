package com.webapp.dao;

import com.webapp.model.Course;
import com.webapp.model.Student;
import com.webapp.model.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SystemDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class StudentMapper implements RowMapper<Student>{
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(String.valueOf(rs.getInt("id")));
            student.setName(rs.getString("name"));
            return student;
        }
    }
    private static final class CourseMapper implements RowMapper<Course>{
        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setCourseID(String.valueOf(rs.getInt("id")));
            course.setCourseName(rs.getString("name"));
            return course;
        }
    }

    private static final class StudentCourseMapper implements RowMapper<StudentCourse>{
        @Override
        public StudentCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setCourseID(String.valueOf(rs.getInt("courseID")));
            studentCourse.setStudentID(String.valueOf(rs.getInt("studentID")));
            studentCourse.setStudentName(rs.getString("student_name"));
            studentCourse.setCourseName(rs.getString("course_name"));
            studentCourse.setGrade(rs.getInt("grade"));
            return studentCourse;
        }
    }


    public void addStudent(Student student){
        jdbcTemplate.update("INSERT INTO student values(? , ?)" , student.getId() , student.getName());

    }
    public Student findStudentByID(String id){
        Student student = jdbcTemplate.queryForObject("SELECT * FROM student WHERE id=?" , new StudentMapper() , id );
        return student;
    }
    public List<Student> findAllStudents(){
        List<Student> list = jdbcTemplate.query("SELECT * FROM student" , new StudentMapper());
        return list ;
    }

    public void addCourse(Course course){
        jdbcTemplate.update("INSERT INTO course values(? , ?)" , course.getCourseID() ,course.getCourseName());

    }
    public Course findCourseByID(String id){
        Course course= jdbcTemplate.queryForObject("SELECT * FROM course WHERE id=?" , new CourseMapper(), id );
        return course;
    }

    public List<Course> findAllCourses(){
        List<Course> list = jdbcTemplate.query("SELECT * FROM course" , new CourseMapper());
        return list;
    }


    public void addCourseToAStudent(String studentID , String courseID, int grade){
        Course course = findCourseByID(courseID);
        Student student = findStudentByID(studentID);
        jdbcTemplate.update("INSERT INTO studentCourse values(?,?,?,?,?)" , student.getId() , student.getName() , course.getCourseID() , course.getCourseName() ,grade );
    }
    public List<Course> findAllCoursesForAStudent(String studentID){
        List<Course> list = jdbcTemplate.query("SELECT courseID as id ,course_name as name FROM studentCourse WHERE studentID="+studentID,
                new CourseMapper());
        return list;
    }
    private List<StudentCourse> getAllGradesOfACourse(String courseID){
        List<StudentCourse> list = jdbcTemplate.query("SELECT * FROM studentCourse WHERE courseID="+courseID,
                new StudentCourseMapper());
        return list;
    }
    public int getMaxGrade(String courseID){
        List<StudentCourse> list = getAllGradesOfACourse(courseID);
        int max = Integer.MIN_VALUE;
        for (StudentCourse course : list)
            if (course.getGrade()>max)
                max = course.getGrade();
        return max;
    }
    public int getMinGrade(String courseID){
        List<StudentCourse> list = getAllGradesOfACourse(courseID);
        int min = Integer.MAX_VALUE;
        for (StudentCourse course : list)
            if (course.getGrade()<min)
                min = course.getGrade();
        return min;
    }
    public double getAvgGrade(String courseID){
        List<StudentCourse> list = getAllGradesOfACourse(courseID);
        double avg = 0;
        for (StudentCourse course : list)
            avg+=course.getGrade();
        return avg/list.size();
    }
    public int getStudentGrade(String courseID , String studentID){
        String sql = "select grade from studentCourse where (studentID=? AND courseID=?)";
        int grade = jdbcTemplate.queryForObject(sql , new Object[]{studentID , courseID} , Integer.class);
        return grade;
    }
}
