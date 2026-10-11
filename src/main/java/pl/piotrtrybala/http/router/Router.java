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

        // Set parameters parsed in URI path
        Handler handler = match.handler();
        handler.setParams(match.parameters());

        return handler;
    }

//    public Route root; // route /
//
//    public Router() {
//        this.root = new Route();
//    }
//
//    public Function<Request, Response> getHandler(Method method, String path) {
//        return this.root.getHandler(method, path);
//    }
//
//    private void addRoute(Method method, String path, Function<Request, Response> handler) {
//        this.root.addRoute(method, path, handler);
//    }
//
//    public void get(String uri, Function<Request, Response> handler) {
//        this.addRoute(Method.GET, uri, handler);
//    }
//    public void post(String uri, Function<Request, Response> handler) {
//        this.addRoute(Method.POST, uri, handler);
//    }
//    public void put(String uri, Function<Request, Response> handler) {
//        this.addRoute(Method.PUT, uri, handler);
//    }
//    public void delete(String uri, Function<Request, Response> handler) {
//        this.addRoute(Method.DELETE, uri, handler);
//    }
//    public void head(String uri, Function<Request, Response> handler) {
//        this.addRoute(Method.HEAD, uri, handler);
//    }
}
