import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

class Server {
    public static void main(String[] args)
    {
        ServerSocket server = null;

        try {

            server = new ServerSocket(8000);
            server.setReuseAddress(true);

            while (true) {
                Socket client = server.accept();

                System.out.println("New client connected"
                        + client.getInetAddress()
                        .getHostAddress());

                ClientHandler handler = new ClientHandler(client);
                new Thread(handler).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}