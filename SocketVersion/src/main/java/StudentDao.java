import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDao {

    private DataSource ds;

    public StudentDao(){
        try{
            ds = getDataSource();
        }catch (Exception e){

        }
    }
    private  DataSource getDataSource() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("webtask");
        ds.setUser("root");
        ds.setPassword("1234");
        ds.setPort(4000);
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);

        return ds;
    }

    public void displayStudentCourses(String studentID , PrintWriter writer) throws SQLException {
        try ( Connection conn = ds.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT student_name ,course_name , courseID , grade FROM studentCourse WHERE studentID="+studentID);
            while (rs.next()) {
                writer.printf("%s: %s -- %s -- %s\n",
                        rs.getString("student_name"),
                        rs.getInt("courseID"),
                        rs.getString("course_name"),
                        rs.getInt("grade"));
            }
            writer.flush();
        }
    }
    public String getStudentName(String studentID){
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
}
