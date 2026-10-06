package pl.piotrtrybala;

import java.util.HashMap;

public class ResponseBuilder {

    private final HTTPVersion version = HTTPVersion.HTTP11;
    private HTTPStatusCode statusCode;
    private HashMap<String, String> headers;
    private ResponseBody body;

    public ResponseBuilder() {
    }

    public ResponseBuilder statusCode(HTTPStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public ResponseBuilder body(ResponseBody body) {
        this.body = body;
        return this;
    }

    public Response build() {
        return new Response(
                this.version,
                this.statusCode,
                this.headers,
                this.body
        );
    }

}
