package pl.piotrtrybala;

import java.net.URL;
import java.util.HashMap;

public class Request {

    // request line
    private HTTPMethod method;
    private URL url;
    private HTTPVersion version;

    // headers
    private HashMap<String, String> headers;

    // body
    private char[] body;

    public Request() {

    }

    public HTTPMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public HTTPVersion getVersion() {
        return version;
    }

    public void setVersion(HTTPVersion version) {
        this.version = version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String header) {
        return this.headers.get(header);
    }

    public char[] getBody() {
        return body;
    }

    public void setBody(char[] body) {
        this.body = body;
    }
}
