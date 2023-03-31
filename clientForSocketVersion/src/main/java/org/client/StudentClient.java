package org.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentClient{
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Socket socket = new Socket( "127.0.0.1",8000);


        BufferedReader reader  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter student name");
        String name = scanner.nextLine();
        writer.println(name);
        writer.flush();
        System.out.println("enter student id");
        String id = scanner.nextLine();
        writer.println(id);
        writer.flush();
        reader.lines().forEach(s-> System.out.println(s));
    }

}