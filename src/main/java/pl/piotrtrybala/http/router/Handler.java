package pl.piotrtrybala.http.router;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Handler {
    private HashMap<String, String> params;
    private final Function<Request, Response> handle;

    public Handler(HashMap<String, String> params, Function<Request, Response> handle) {
        this.handle = handle;
        this.params = params;
    }

    public Function<Request, Response> getHandle() {
        return handle;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
    public HashMap<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Handler{" +
                "params=" + params +
                ", handle=" + handle +
                '}';
    }
}
