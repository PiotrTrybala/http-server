package pl.piotrtrybala;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    private final ServerConfig config;
    private final Dispatcher dispatcher;

    private Socket clientSocket;
    private ServerSocket serverSocket;

    public Server(ServerConfig config) {
        this.config = config;
        this.dispatcher = new Dispatcher();
        this.init();
    }

    private void init() {
        try {

            serverSocket = new ServerSocket(this.config.port);

            while (true) {

                try {
                    clientSocket = serverSocket.accept();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream())
                    );

                    String line;
                    int contentLength = 0;

                    List<String> requestHeader = new ArrayList<>();

                    while((line = reader.readLine()) != null && !line.isEmpty()) {
                        requestHeader.add(line);
                        if (line.toLowerCase().startsWith("content-length:")) {
                            contentLength = Integer.parseInt(line.substring(15).trim());
                        }
                    }

                    char[] requestBody = new char[this.config.maxRequestSize];

                    if (contentLength > 0) {
                        int result = reader.read(requestBody, 0, contentLength);
                    }

                    Request request = Request.parse(requestHeader, requestBody);
                    Response response = this.dispatcher.dispatch(request);
                    byte[] output = response.toBytes();

                    BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
                    outputStream.write(output, 0, output.length);

                    outputStream.close();
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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