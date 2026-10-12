package pl.piotrtrybala.http.router;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.types.Method;

import java.util.HashMap;
import java.util.function.Function;

public class Router {

    private Route root;

    public Router() {
        this.root = new Route("/");
    }

    public Handler getHandler(Request request) {

        Route.RouteMatch match = this.root.getRouteMatch(request.getMethod(), request.getUri().getPath());
        if (match == null) return null;

        System.out.println(match.toString());


        // Set parameters parsed in URI path
        Handler handler = match.handler();
        handler.setParams(match.parameters());

        return handler;
    }

    public void get(String path, Function<Request, Response> handler) {
        this.root.addRoute(Method.GET, path, handler);
    }
    public void post(String path, Function<Request, Response> handler) {
        this.root.addRoute(Method.POST, path, handler);
    }
    public void put(String path, Function<Request, Response> handler) {
        this.root.addRoute(Method.PUT, path, handler);
    }
    public void delete(String path, Function<Request, Response> handler) {
        this.root.addRoute(Method.DELETE, path, handler);
    }
    public void head(String path, Function<Request, Response> handler) {
        this.root.addRoute(Method.HEAD, path, handler);
    }

    public void print() {
        this.root.print();
    }
}
