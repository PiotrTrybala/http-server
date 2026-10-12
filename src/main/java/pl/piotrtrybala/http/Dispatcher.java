package pl.piotrtrybala.http;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.router.Handler;
import pl.piotrtrybala.http.router.Router;

import java.util.function.Function;

public class Dispatcher {


    public Dispatcher() {
    }

    public Response dispatch(Request request, Handler handler) {
        System.out.println("Dispatcher handler = " + handler);
        if (handler == null) {
            return Response.notFound();
        }
        request.setParams(handler.getParams());
        return handler.getHandle().apply(request);
    }
}
