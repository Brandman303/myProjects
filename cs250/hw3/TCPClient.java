package cs250.hw3;

import java.io.*;
import java.net.*;
import java.io.IOException;
import java.util.Random;


public class TCPClient {
    static DataInputStream din;
    static DataOutputStream dout;
    static Socket clientSocket;
    public static void main(String[] args) {
        String[] arg = {"localhost", "8080"}; // arg for testing
        String serverHost = args[0]; //this is the server ip I believe
        int serverPort = Integer.parseInt(args[1]);

        try {
            //System.out.println("Attempting connection");
            clientSocket = new Socket(serverHost, serverPort);
            dout = new DataOutputStream(clientSocket.getOutputStream());
            din = new DataInputStream(clientSocket.getInputStream());


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        try {
            int serverMessage1 = din.readInt();
            System.out.println("Received config");
            System.out.println("number of messages = " + serverMessage1);
            int serverMessage2 = din.readInt();
            System.out.println("seed = " + serverMessage2);
            Random rand = new Random(serverMessage2);
           // dout.writeInt(serverMessage2); //change what this was used in the server side.
            try {
                Thread.sleep(10000); //not sure if this goes before the writeInt(serverMessage2) or after
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("Starting to send messages to server...");
            long senderSum = 0;
            int numOfSentMessages = 0;
           // dout.flush();

            for(int i = 0; i < serverMessage1; i++) {
                int currentNum = rand.nextInt();
               // System.out.println("Generated int: " + currentNum);
              //  System.out.println("Cumulative long sum: " + senderSum);
                dout.writeInt(currentNum);
                senderSum += currentNum;
                numOfSentMessages++;
               
            }

            System.out.println("Finished sending messages to server.");
            System.out.println("Total messages sent: " + numOfSentMessages);
            System.out.println("Sum of messages sent: " + senderSum);
           // dout.flush(); was the end maybe uncomment if the dout.flush() at the end messes things up

            long receiverSum = 0;
            int numOfReceivedMessages = 0;
            System.out.println("Starting to listen for messages for server...");

            for (int i = 0; i < serverMessage1; i++) {
                receiverSum += din.readInt();
                numOfReceivedMessages++;
            }
            System.out.println("Finished listening for messsages from server.");
            System.out.println("Total messages received: " + numOfReceivedMessages);
            System.out.println("Sum of messages received: " + receiverSum); //ensure receiver Sum is getting only forwarded messages. I do not know if that includes since the beginning of the program or up to this listening point. So make sure that there is only relevant values in the stream.
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try{
                if (dout != null) dout.close();
                if (din != null) din.close();
                if (clientSocket != null) clientSocket.close();
            }
            catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
