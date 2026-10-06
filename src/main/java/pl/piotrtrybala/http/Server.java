package pl.piotrtrybala.http;

import pl.piotrtrybala.http.request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final Config config;

    private final Dispatcher dispatcher;

    private Socket clientSocket;
    private ServerSocket serverSocket;

    public Server(Config config) throws Exception {
        this.config = config;

        this.dispatcher = new Dispatcher();
        this.initHttpServer();
    }

    private void initHttpServer() throws Exception {

        serverSocket = new ServerSocket(this.config.port);

        while (true) {
            try {
                clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                char[] buffer = new char[this.config.maxRequestSize];

                int size = in.read(buffer, 0, this.config.maxRequestSize);
                if (size == -1) {
                    // error: reading error
                    System.out.println("failed while reading data");
                }
                Request request = Request.fromBuffer(buffer);

                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


}
