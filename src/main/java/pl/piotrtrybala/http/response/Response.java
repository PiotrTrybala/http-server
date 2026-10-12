package pl.piotrtrybala.http.response;

import pl.piotrtrybala.http.types.StatusCode;
import pl.piotrtrybala.http.types.Version;

import java.util.HashMap;

public class Response {

    private Version version;
    private StatusCode statusCode;

    private HashMap<String, String> headers;

    private Body body;

    public Response() {
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

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "version=" + version +
                ", statusCode=" + statusCode +
                ", headers=" + headers +
                ", body=" + body +
                '}';
    }

    public static Response ok() {
        Response response = new Response();
        response.setVersion(Version.HTTP11);
        response.setStatusCode(StatusCode.OK);
        return response;
    }

    public static Response notFound() {
        Response response = new Response();
        response.setVersion(Version.HTTP11);
        response.setStatusCode(StatusCode.NOT_FOUND);
        return response;
    }

    public static Response badRequest() {
        Response response = new Response();
        response.setVersion(Version.HTTP11);
        response.setStatusCode(StatusCode.NOT_FOUND);
        return response;
    }

    public static Response internalServerError() {
        Response response = new Response();
        response.setVersion(Version.HTTP11);
        response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
        return response;
    }

    public char[] toBuffer() {

        String builder = "HTTP/1.1" +
                " " +
                statusCode.toString() +
                "\r\n" +
                "Server: shttp" + // add to config
                "\r\n" +
                "Content-Length: 0" + // dummy response
                "\r\n" +
                "Connection: close" +
                "\r\n" +
                "\r\n";

        return builder.toCharArray();
    }
}
