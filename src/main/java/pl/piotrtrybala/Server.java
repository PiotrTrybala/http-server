package pl.piotrtrybala;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    private ServerConfig config;
    private Dispatcher dispatcher;

    public Socket socket;
    public

    public Server(ServerConfig config) {
        this.config = config;
        this.init();
    }

    private void init() {

    }

//    private Socket socket = null;
//    private ServerSocket serverSocket = null;
//    private BufferedInputStream in = null;
//
//    public Server(int port) throws IOException {
//        try (ServerSocket serverSocket = new ServerSocket(port)) {
//            System.out.println("Server started on port " + port);
//
//            while (true) {
//                try (Socket socket = serverSocket.accept()) {
//                     System.out.println("Client accepted");
//
//                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                     String line;
//                     int contentLength = 0;
//
//                     while((line = reader.readLine()) != null && !line.isEmpty()) {
//                         System.out.println(line);
//
//                         if (line.toLowerCase().startsWith("content-length:")) {
//                             contentLength = Integer.parseInt(line.substring(15).trim());
//                         }
//                     }
//
//                     if (contentLength > 0) {
//                         char[] bodyBuffer = new char[contentLength];
//                         int bytesRead = reader.read(bodyBuffer, 0, contentLength);
//                         String body = new String(bodyBuffer, 0, contentLength);
//                         System.out.println("Body: " + body);
//                     }
//
//                     socket.close();
//                     reader.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
}