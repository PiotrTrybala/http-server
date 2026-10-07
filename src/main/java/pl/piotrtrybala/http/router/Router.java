package pl.piotrtrybala.http.router;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.types.Method;

import java.util.HashMap;
import java.util.function.Function;

public class Router {

    private final HashMap<Route, Function<Request, Response>> routes;

    public Router() {
        this.routes = new HashMap<>();
    }

    public Function<Request, Response> getHandler(Method method, String uri) {
        return this.routes.get(new Route(method, uri));
    }

    private void addRoute(Method method, String uri, Function<Request, Response> handler) {
        this.routes.put(new Route(method, uri), handler);
    }

    public void get(String uri, Function<Request, Response> handler) {
        this.addRoute(Method.GET, uri, handler);
    }
    public void post(String uri, Function<Request, Response> handler) {
        this.addRoute(Method.POST, uri, handler);
    }
    public void put(String uri, Function<Request, Response> handler) {
        this.addRoute(Method.PUT, uri, handler);
    }
    public void delete(String uri, Function<Request, Response> handler) {
        this.addRoute(Method.DELETE, uri, handler);
    }
    public void head(String uri, Function<Request, Response> handler) {
        this.addRoute(Method.HEAD, uri, handler);
    }
}
