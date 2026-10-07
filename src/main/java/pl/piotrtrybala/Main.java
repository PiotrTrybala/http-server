package pl.piotrtrybala;

import pl.piotrtrybala.http.Config;
import pl.piotrtrybala.http.Server;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.router.Router;

public class Main {
    static void main(String[] args) {
        Config httpConfig = new Config();

        try {
            Server server = new Server(httpConfig);

            Router router = server.getRouter();

            router.get("/data", (request) -> {
                System.out.println("Executing /data route");
                return Response.ok();
            });
            router.get("/params/{id1}/{id2}", (request) -> Response.ok());
            router.get("/json", (request) -> Response.ok());
            router.post("/form", (request) -> Response.ok());
            router.put("/file/{id}", (request) -> Response.ok());
            router.delete("/file/{id}", (request) -> Response.ok());
            router.head("/info", (request) -> Response.ok());

            server.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
