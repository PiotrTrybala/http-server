package pl.piotrtrybala.http.response;

import pl.piotrtrybala.ResponseBody;
import pl.piotrtrybala.http.types.StatusCode;
import pl.piotrtrybala.http.types.Version;

import java.util.HashMap;

public class Response {

    private Version version;
    private StatusCode statusCode;

    private HashMap<String, String> headers;

    private ResponseBody body;

    public Response(Version version, StatusCode statusCode, HashMap<String, String> headers, ResponseBody body) {
        this.version = version;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
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

    public char[] toBuffer(Response response) {
        return null;
    }
}
