package com.example.chatappclientservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startClient();
    }

    public void startClient() {
        try {

            String serverIp = "127.0.0.1";
            String serverPort = "12345";

            InetAddress ip = InetAddress.getByName(serverIp);
            socket = new Socket(ip, Integer.parseInt(serverPort));
            System.out.println("Connected to server: " + serverIp + " on port: " + serverPort);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create a thread to listen for messages from the server
            // Thread listenThread = new Thread(() -> {
            out.println("hii I am Client ");
            // try {
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Server: " + response);
            }
            // } catch (IOException e) {
            // System.err.println("Error in listenThread: " + e.getMessage());
            // }
            // });
            // listenThread.start();

            // Read messages from the console and send to the server
            Scanner scanner = new Scanner(System.in);
            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
            }

            // Clean up resources
            stopClient();
        } catch (Exception e) {
            System.err.println("Error in startClient: " + e.getMessage());
        }
    }

    public void stopClient() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
            System.out.println("Client stopped.");
        } catch (IOException e) {
            System.err.println("Error in stopClient: " + e.getMessage());
        }
    }
}
