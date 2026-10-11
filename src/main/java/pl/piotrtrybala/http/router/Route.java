package pl.piotrtrybala.http.router;

import pl.piotrtrybala.http.request.Request;
import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.types.Method;
import pl.piotrtrybala.utils.Pair;

import java.util.*;
import java.util.function.Function;

public class Route {

    private static int ROUTER_NESTING_LIMIT = 10;

    private String segment;
    private SortedSet<Pair<String, Route>> routes;
    private Map<Method, Handler> handlers;

    public record RouteMatch(Handler handler, Map<String, String> parameters) { }

    public Route(String segment) {
        this.segment = segment;
        this.routes = new TreeSet<>(Comparator.comparing(Pair::getKey));
        this.handlers = new HashMap<>();
    }

    public void addRoute(Method method, String path, Function<Request, Response> handler) {

        if (path == null || path.isEmpty()) return;

        String[] segments = path.split("/");
        Route currentSegment = this;
        int level = 0;

        for (String segment : segments) {
            if (segment.isEmpty()) return;
            if (level >= ROUTER_NESTING_LIMIT) return;

            Route nextSegment = null;
            for (Pair<String, Route> pair : currentSegment.routes) {
                if (segment.equals(pair.getKey())) {
                    nextSegment = pair.getValue();
                    break;
                }
            }

            if (nextSegment == null) {
                nextSegment = new Route(segment);
                currentSegment.routes.add(new Pair<>(segment, nextSegment));
            }

            currentSegment = nextSegment;
            level++;
        }

        currentSegment.handlers.put(method, new Handler(new HashMap<>(), handler));
    }

    public RouteMatch getRouteMatch(Method method, String path) {
        return matchRoute(method, path);
    }

    public RouteMatch matchRoute(Method method, String path) {
        Map<String, String> parameters = new HashMap<>();

        if (path == null || path.isEmpty()) return new RouteMatch(null, new HashMap<>());

        String[] segments = path.split("/");
        Route currentSegment = this;
        int level = 0;

        for (String segment : segments) {
            if (segment.isEmpty()) continue;
            if (level >= ROUTER_NESTING_LIMIT) break;

            Route nextSegment = null;
            String matchedKey = null;

            // Do exact match of route
            for (Pair<String, Route> pair : currentSegment.routes) {
                if (segment.equals(pair.getKey())) {
                    nextSegment = pair.getValue();
                    matchedKey = pair.getKey();
                    break;
                }
            }

            // Do parameter match of route
            if (nextSegment == null) {
                for (Pair<String, Route> pair : currentSegment.routes) {
                    String key = pair.getKey();
                    if (key.startsWith("{") && key.endsWith("}")) {
                        nextSegment = pair.getValue();
                        matchedKey = key;
                        break;
                    }
                }
            }
            // If none found, return null handler
            if (nextSegment == null) {
                return new RouteMatch(null, new HashMap<>());
            }
            /*
                If parameter is found, then assign current segment to that key
                Example:
                Handler: /{param1}/a/b/c
                Route:   /abc/a/b/c
                Segments: [abc, a, b, c]
                No exact match found, then {param1} is stripped to param1, then:
                param1 -> abc (which is current segment in processing)
             */
            if (matchedKey.startsWith("{") && matchedKey.endsWith("}")) {
                String paramName = matchedKey.substring(1, matchedKey.length() - 1);
                parameters.put(paramName, segment);
            }

            currentSegment = nextSegment;
            level++;
        }

        Handler handler = currentSegment.handlers.getOrDefault(method, null);
        return new RouteMatch(handler, parameters);
    }
}