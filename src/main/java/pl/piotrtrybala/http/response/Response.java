package pl.piotrtrybala.http.response;

import com.google.gson.Gson;
import pl.piotrtrybala.http.types.StatusCode;
import pl.piotrtrybala.http.types.Version;

import java.util.HashMap;

public class Response {

    private static Gson gson = new Gson();

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

    public <T> void json(Object object) {
        String json = gson.toJson(object);
        this.body = new JsonBody(json);
    }

    public static Response internalServerError() {
        Response response = new Response();
        response.setVersion(Version.HTTP11);
        response.setStatusCode(StatusCode.INTERNAL_SERVER_ERROR);
        return response;
    }

    private static String SEPARATOR = "\r\n";

    public char[] toBuffer() {

        StringBuilder builder = new StringBuilder();

        builder.append("HTTP/1.1")
                .append(" ")
                .append(statusCode.toString())
                .append(SEPARATOR);

        int bodySize = (body != null) ? body.size() : 0;
        builder.append("Content-Length: ").append(bodySize).append(SEPARATOR);

        if (headers != null && !headers.isEmpty()) {
            headers.forEach((key, value) -> {
                builder.append(key).append(": ").append(value).append(SEPARATOR);
            });
            if (!headers.containsKey("Connection")) {
                builder.append("Connection: close").append(SEPARATOR);
            }
        } else {
            builder.append("Connection: close").append(SEPARATOR);
        }

        builder.append(SEPARATOR);

        if (body != null) {
            builder.append(body.getContent());
        }

        System.out.println(builder);

        return builder.toString().toCharArray();
    }
}
