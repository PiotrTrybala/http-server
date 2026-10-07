package pl.piotrtrybala.http;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.router.Router;

import java.util.function.Function;

public class Dispatcher {

    public Router router;

    public Dispatcher(Router router) {
        this.router = router;
    }

    public Response dispatch(Request request) {
        String path = request.getUri().getPath();
        System.out.println("dispatch, path = " + path);
        Function<Request, Response> handler = this.router.getHandler(request.getMethod(), path);

        if (handler == null) {
            return Response.notFound();
        }
        System.out.println("handler = " + handler.toString());

        return handler.apply(request);
    }
}
