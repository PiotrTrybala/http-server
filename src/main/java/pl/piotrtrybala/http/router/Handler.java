package pl.piotrtrybala.http.router;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;

import java.util.function.Function;

public class Handler {
    private Function<Request, Response> handle;

    public Handler(Function<Request, Response> handle) {
        this.handle = handle;
    }

    public Function<Request, Response> getHandle() {
        return handle;
    }
}
