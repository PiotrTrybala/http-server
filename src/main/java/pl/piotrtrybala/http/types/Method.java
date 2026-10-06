package pl.piotrtrybala.http.types;

public enum Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    public String getMethod() {
        return this.method;
    }

    Method(String method) {
        this.method = method;
    }
}
