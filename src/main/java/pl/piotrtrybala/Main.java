package pl.piotrtrybala;

import pl.piotrtrybala.http.Config;
import pl.piotrtrybala.http.Server;
import pl.piotrtrybala.http.response.JsonBody;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.router.Router;
import pl.piotrtrybala.http.types.StatusCode;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Main {
    static void main(String[] args) {
        Config httpConfig = new Config();

        try {
            Server server = new Server(httpConfig);

            Router router = server.getRouter();

            router.post("/data", (request) -> {
                HashMap<String, String> form = request.formData();
                System.out.println("data form = " + form);
                return Response.ok();
            });
            router.get("/json", (request) -> {
                Data.Input input = request.json(Data.Input.class);
                System.out.println("JSON input =" + input.name);
                Response response = new Response();
                response.setStatusCode(StatusCode.OK);

                response.json(new Data.Output(input.name));

                return response;
            });
            router.get("/params/{id1}/{id2}", (request) -> {
                HashMap<String, String> params = request.params();
                System.out.println(params + " = " + params.get("id1"));
                return Response.ok();
            });
            router.post("/form", (request) -> Response.ok());
            router.put("/file/{id}", (request) -> Response.ok());
            router.delete("/file/{id}", (request) -> Response.ok());
            router.head("/info", (request) -> Response.ok());

            router.print();

            server.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
