package pl.piotrtrybala.http;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.router.Router;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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

        serverSocket = new ServerSocket(this.config.port);
    }

    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                clientSocket = serverSocket.accept();
                handleClient(clientSocket);
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Router getRouter() {
        return this.router;
    }

    private void handleClient(Socket clientSocket) throws IOException {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            char[] buffer = new char[this.config.maxRequestSize];

            int size = in.read(buffer, 0, this.config.maxRequestSize);
            if (size == -1) {
                System.out.println("failed while reading data: eof");
                return;
            }

            Request request = Request.fromBuffer(buffer);
            if (request == null) {
                System.out.println("failed to parse request");
                return;
            }
            System.out.println("request = " + request.toString());

            Response response = this.dispatcher.dispatch(request);
            System.out.println("response = " + response.toString());

            char[] responseBuffer = response.toBuffer();
            System.out.println("char buffer = " + Arrays.toString(responseBuffer));

            out.write(responseBuffer);
            out.flush();
        } catch (IOException ex) {
            System.err.println("Error handling client connection: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


}
