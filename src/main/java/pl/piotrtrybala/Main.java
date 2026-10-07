package pl.piotrtrybala;

import pl.piotrtrybala.http.Config;
import pl.piotrtrybala.http.Server;
import pl.piotrtrybala.http.router.Router;

import java.io.IOException;

public class Main {
    static void main(String[] args) {
        Config httpConfig = new Config();

        try {
            Server server = new Server(httpConfig);

            Router router = server.getRouter();
            router.get("/", (request) -> null);
            router.post("/", (request) -> null);
            router.get("/abc", (request) -> null);
            router.head("/abc", (request) -> null);
            router.put("/file", (request) -> null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
