package pl.piotrtrybala;

import java.util.HashMap;

public class Response {

    private HTTPVersion version;
    private HTTPStatusCode statusCode;

    private HashMap<String, String> headers;

    private ResponseBody body;

    public Response(HTTPVersion version, HTTPStatusCode statusCode, HashMap<String, String> headers, ResponseBody body) {
        this.version = version;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public HTTPVersion getVersion() {
        return version;
    }

    public void setVersion(HTTPVersion version) {
        this.version = version;
    }

    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HTTPStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public ResponseBody getBody() {
        return body;
    }

    public void setBody(ResponseBody body) {
        this.body = body;
    }

    public byte[] toBytes() {
        return null;
    }
}
