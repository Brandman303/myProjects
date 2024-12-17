package cs250.hw3;

import java.io.*;
import java.net.*;
import java.util.Random;

public class TCPServer2 {
    static DataInputStream dinOne;
    static DataInputStream dinTwo;
    static DataOutputStream doutOne;
    static DataOutputStream doutTwo;
    static Socket clientSocketOne;
    static Socket clientSocketTwo;
    static ServerSocket serverSocket;

    public static void main(String[] args) {
        int portNum = Integer.parseInt(args[0]);
        int seed = Integer.parseInt(args[1]);
        int numMsg = Integer.parseInt(args[2]);
        Random rand = new Random(seed); // do I need this?

        try {
            System.out.println("IP Address: " + InetAddress.getLocalHost() + 
            "\nPort Number " + portNum);

            serverSocket = new ServerSocket(portNum);
            System.out.println("wating for client...");

            //Blocking until accepted
            clientSocketOne = serverSocket.accept();
            clientSocketTwo = serverSocket.accept();
            System.out.println("Clients Connected!");

            System.out.println("Sending config to clients...");

            doutOne = new DataOutputStream(clientSocketOne.getOutputStream());
            dinOne = new DataInputStream(clientSocketOne.getInputStream());
            for (String arg : args) {
                sendToClientOne(Integer.parseInt(arg));
            }

            int rand1 = rand.nextInt();
            int rand2 = rand.nextInt();


        } catch (BindException exception) {
            System.err.println(exception.getMessage());
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static void sendToClientOne(int msg) {
        try {
            doutOne.writeInt(msg);
            doutOne.flush();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static void sendToClientTwo(int msg) {
        try {
            doutTwo.writeInt(msg);
            doutTwo.flush();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}