package pl.piotrtrybala;

public enum HTTPMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    public String getMethod() {
        return this.method;
    }

    HTTPMethod(String method) {
        this.method = method;
    }
}
