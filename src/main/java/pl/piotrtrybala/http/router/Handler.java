package pl.piotrtrybala.http.router;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Handler {
    private Map<String, String> params;
    private final Function<Request, Response> handle;

    public Handler(Map<String, String> params, Function<Request, Response> handle) {
        this.handle = handle;
        this.params = params;
    }

    public Function<Request, Response> getHandle() {
        return handle;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public Map<String, String> getParams() {
        return params;
    }
}
