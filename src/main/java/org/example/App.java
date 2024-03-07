package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    static int portNumber;

    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);
        System.out.println("Server started!");
        for(;;){
            ServerSocket serverSocket = null;
            serverSocket = getServerSocket(portNumber);
            Socket clientSocket = null;

            while(true){
                clientSocket = accept(serverSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.handle();
            }

        }
    }
    private static Socket accept(ServerSocket serverSocket) {
        Socket clientSocket;
        try {
            clientSocket = serverSocket.accept();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return clientSocket;
    }

    private static ServerSocket getServerSocket(int portNumber) {
        ServerSocket serverSocket;
        try {
            //noinspection resource
            serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return serverSocket;
    }
}
