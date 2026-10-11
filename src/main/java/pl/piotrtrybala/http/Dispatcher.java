package pl.piotrtrybala.http;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.router.Router;

import java.util.function.Function;

public class Dispatcher {


    public Dispatcher() {
    }

    public Response dispatch(Request request, Function<Request, Response> handler) {
        if (handler == null) {
            return Response.notFound();
        }
        return handler.apply(request);
    }
}
