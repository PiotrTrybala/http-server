package pl.piotrtrybala.http.router;

import pl.piotrtrybala.http.types.Method;

import java.util.Objects;

public class Route {
    private Method method;
    private String url;

    public Route(Method method, String url) {
        this.method = method;
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return method == route.method && Objects.equals(url, route.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method.toString(), url);
    }
}
