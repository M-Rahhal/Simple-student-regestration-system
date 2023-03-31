import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
            try {


                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                String name = reader.readLine();
                String id = reader.readLine();
                if (!isValidStudent(name, id)) {
                    writer.println("invalid user");
                    writer.flush();
                    System.out.println("client disconnected");
                    clientSocket.close();
                    return;
                }
                StudentDao dao = new StudentDao();
                dao.displayStudentCourses(id, writer);
                clientSocket.close();
            }
            catch (Exception e){}
    }



    private static boolean isValidStudent(String name , String id){
        StudentDao dao = new StudentDao();
        String n = dao.getStudentName(id);
        if (n==null)
            return false;
        if (n.equals(name))
            return true;
        return false;
    }
}
