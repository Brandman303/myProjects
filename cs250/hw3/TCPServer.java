package cs250.hw3;

import java.io.*;
import java.net.*;
import java.util.Random;

public class TCPServer {
    static DataInputStream dinOne;
    static DataInputStream dinTwo;
    static DataOutputStream doutOne;
    static DataOutputStream doutTwo;
    static Socket clientSocketOne;
    static Socket clientSocketTwo;
    static ServerSocket serverSocket;

    public static void main(String[] args) {
        String[] arg = {"8080", "24", "2"}; //arg for testing
        int portNum = Integer.parseInt(args[0]);
        int seed = Integer.parseInt(args[1]);
        int numMsg = Integer.parseInt(args[2]);
        Random rand = new Random(seed);
       
        try {

            System.out.println("IP Address: " + InetAddress.getLocalHost() + "\nPort Number " + portNum);

            serverSocket = new ServerSocket(portNum);
            System.out.println("waiting for client...");

            clientSocketOne = serverSocket.accept();
            doutOne = new DataOutputStream(clientSocketOne.getOutputStream());
            dinOne = new DataInputStream(clientSocketOne.getInputStream());

            clientSocketTwo = serverSocket.accept();
            doutTwo = new DataOutputStream(clientSocketTwo.getOutputStream());
            dinTwo = new DataInputStream(clientSocketTwo.getInputStream());

            System.out.println("Clients Connected!");

            System.out.println("Sending config to clients...");

            int rand1 = rand.nextInt();
            int rand2 = rand.nextInt();
            sendToClient(doutOne, numMsg);
            sendToClient(doutOne, rand1);
            sendToClient(doutTwo, numMsg);
            sendToClient(doutTwo, rand2);
            System.out.println(clientSocketTwo.getInetAddress().getHostName() + " " + rand1);
            System.out.println(clientSocketOne.getInetAddress().getHostName() + " " + rand2);

            System.out.println("Finished sending config to clients.");
            System.out.println("Starting to listen for client messages...");

            long clientOneSum = 0;
            int clientOneMsgCount = 0;
            for (int i = 0; i < numMsg; i++) {
                int c1msg = dinOne.readInt();
                clientOneSum += c1msg;
                doutTwo.writeInt(c1msg);
                doutTwo.flush();
                clientOneMsgCount++;
            }

            long clientTwoSum = 0;
            int clientTwoMsgCount = 0;
            for (int i = 0; i < numMsg; i++) {
                int c2msg = dinTwo.readInt();
                clientTwoSum += c2msg;
                doutOne.writeInt(c2msg);
                doutOne.flush();
                clientTwoMsgCount++;
            }

            System.out.println("Finished listening for client messages.");

            System.out.println(clientSocketTwo.getInetAddress().getHostName() + "\n\tMessages received: " + clientOneMsgCount
            + "\n\tSum received: " + clientOneSum);
            System.out.println(clientSocketOne.getInetAddress().getHostName() + "\n\tMessages received: " + clientTwoMsgCount
            + "\n\tSum received: " + clientTwoSum);
        }
        catch (BindException exception) {
            System.err.println(exception.getMessage()); // use exception.getMessage()
        }
        catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        finally {
            try {
                if (doutOne != null) doutOne.close();
                if (doutTwo != null) doutTwo.close();

                if (dinOne != null) dinOne.close();
                if (dinTwo != null) dinTwo.close();

                if (clientSocketOne != null) clientSocketOne.close();
                if (clientSocketTwo != null) clientSocketTwo.close();
                if (serverSocket != null) serverSocket.close();
            } 
            catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }    
    }

        // //sends number of messages that are expected from clients to the clients
        // try {
        //     System.out.println("Sending config to clients...");

        //     int rand1 = rand.nextInt();
        //     int rand2 = rand.nextInt();
        //     sendToClient(clientSocketOne, numMsg, rand2);
        //     sendToClient(clientSocketTwo, numMsg, rand1); // move rand.nextInt to the function so that it's different?
        //     System.out.println(clientSocketTwo.getInetAddress().getHostName() + " " + rand1);
        //     System.out.println(clientSocketOne.getInetAddress().getHostName() + " " + rand2);

        //     System.out.println("Finished sending config to clients.");
        //     System.out.println("Starting to listen for client messages...");
        //     dout =  new DataOutputStream(clientSocketTwo.getOutputStream());
        //     din =  new DataInputStream(clientSocketOne.getInputStream());
        //     int fwdMsg = 0;
        //     for (int i = 0; i < numMsg; i++) {
        //     dout.writeInt(din.readInt()); //write to client two using din read from client one
        //     }
        //     dout.flush();
        // }
        // catch (Exception exception) {
        //     System.err.println(exception.getMessage());
        // }
   

    /*
     * To-Do:
     * int randomNum to Random rand??
     */
    public static void sendToClient(DataOutputStream dout, int msg) {
       try {
        dout.writeInt(msg);
        dout.flush();
       }
       catch (Exception e) {
        System.err.println(e.getMessage());
       }
        //return -1;
    }
}
