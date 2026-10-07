package pl.piotrtrybala.http;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.router.Router;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Server {

    private final Config config;

    private final Dispatcher dispatcher;
    private final Router router;

    private Socket clientSocket;
    private ServerSocket serverSocket;

    public Server(Config config) throws Exception {
        this.config = config;

        this.router = new Router();
        this.dispatcher = new Dispatcher(this.router);
        this.initHttpServer();
    }

    private void initHttpServer() throws Exception {

        serverSocket = new ServerSocket(this.config.port);

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                handleClient(clientSocket);
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void handleClient(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        char[] buffer = new char[this.config.maxRequestSize];

        int size = in.read(buffer, 0, this.config.maxRequestSize);
        if (size == -1) {
            // error: reading error
            System.out.println("failed while reading data");
        }
        Request request = Request.fromBuffer(buffer);

        Response response = this.dispatcher.dispatch(request);
        char[] responseBuffer = response.toBuffer(response);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter((clientSocket.getOutputStream())));
        out.write(responseBuffer);

        in.close();
        out.close();
    }


}
