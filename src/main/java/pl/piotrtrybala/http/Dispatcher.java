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
        Function<Request, Response> handler = this.router.getHandler(request.getMethod(), path);

        if (handler == null) {
            // error: 404 - route not found
            return null;
        }

        return handler.apply(request);
    }


}
