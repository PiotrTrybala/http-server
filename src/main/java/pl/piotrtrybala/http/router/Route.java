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

    public record RouteMatch(Handler handler, HashMap<String, String> parameters) {
        @Override
        public String toString() {
            return "RouteMatch{" +
                    "handler=" + handler +
                    ", parameters=" + parameters +
                    '}';
        }
    }

    public Route(String segment) {
        this.segment = segment;
        this.routes = new TreeSet<>(Comparator.comparing(Pair::getKey));
        this.handlers = new HashMap<>();
    }

    public void addRoute(Method method, String path, Function<Request, Response> handler) {

        if (path == null || path.isEmpty()) return;

        String[] raw = path.split("/");
        List<String> segments = Arrays.stream(raw).skip(1).toList();
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
        HashMap<String, String> parameters = new HashMap<>();

        System.out.println("path = " + path + ", path = " + path);

        if (path == null || path.isEmpty()) return new RouteMatch(null, new HashMap<>());

        String[] raw = path.split("/");
        List<String> segments = Arrays.stream(raw).skip(1).toList();
        Route currentSegment = this;
        int level = 0;

        System.out.println(segments + ", " + currentSegment);

        for (String segment : segments) {
            if (segment.isEmpty()) continue;
            if (level >= ROUTER_NESTING_LIMIT) break;

            Route nextSegment = null;
            String matchedKey = null;

            // Do exact match of route
            for (Pair<String, Route> pair : currentSegment.routes) {
                System.out.println("#" + level + ": Exact match = [" + pair.getKey() + ", " + pair.getValue() + "];");
                if (segment.equals(pair.getKey())) {
                    nextSegment = pair.getValue();
                    matchedKey = pair.getKey();
                    break;
                }
            }

            if (nextSegment != null) {
                nextSegment.print();
            }

            // Do parameter match of route
            if (nextSegment == null) {
                for (Pair<String, Route> pair : currentSegment.routes) {
                System.out.println("#" + level + ": Param match = [" + pair.getKey() + ", " + pair.getValue() + "];");
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

    public void print() {
        this.printHelper(this);
    }

    private void printHelper(Route route) {
        System.out.println("Segment = " + this.segment);
        route.handlers.forEach((k, v) ->
                System.out.println(k + ": " + v)
        );

        for (Pair<String, Route> pair : route.routes) {
            Route child = pair.getValue();
            if (child == null) continue;
            this.printHelper(child);
        }
    }
}