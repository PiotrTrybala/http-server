package pl.piotrtrybala.http.request;

import pl.piotrtrybala.http.response.Response;
import pl.piotrtrybala.http.types.Method;
import pl.piotrtrybala.http.types.Version;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class Request {

    private Method method;
    private URI uri;
    private Version version;

    private HashMap<String, String> params = new HashMap<>();
    private HashMap<String, String> query = new HashMap<>();

    private HashMap<String, String> headers;

    private char[] body;

    public Request() {
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public HashMap<String, String> queryParams() {
        return query;
    }

    public void setQuery(HashMap<String, String> query) {
        this.query = query;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public char[] getBody() {
        return body;
    }

    public void setBody(char[] body) {
        this.body = body;
    }

    public static Request fromBuffer(char[] buffer) throws Exception {

        if (buffer == null || buffer.length == 0) {
            return null;
        }

        Request request = new Request();

        CharBuffer charBuffer = CharBuffer.wrap(buffer);

        // PARSE REQUEST LINE
        String requestLine = readLine(charBuffer);
        if (requestLine == null || requestLine.isEmpty()) return null;

        String[] parts = requestLine.split(" ");
        if (parts.length != 3) return null;

        Method method;
        URI uri;
        Version version;

        try {
            method = Method.valueOf(parts[0].toUpperCase());
            uri = URI.create(parts[1]);
            version = Version.HTTP11;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        request.setMethod(method);
        request.setUri(uri);
        request.setVersion(version);

        HashMap<String, String> query = Request.parseQuery(uri.getQuery());
        request.setQuery(query);

        request.setParams(new HashMap<>());

        // PARSE HEADERS
        HashMap<String, String> headers = new HashMap<>();
        String headerLine;

        while ((headerLine = readLine(charBuffer)) != null && !headerLine.isEmpty()) {
            int colorIndex = headerLine.indexOf(":");
            if (colorIndex != -1) {
                String headerName = headerLine.substring(0, colorIndex).trim();
                String headerValue = headerLine.substring(colorIndex + 1).trim();
                headers.put(headerName, headerValue);
            }
        }

        request.setHeaders(headers);


        // READ BODY
        char[] body = null;
        try {
            int contentLength = Integer.parseInt(headers.get("Content-Length")); // error content length could be not defined
            body = new char[contentLength];
            charBuffer.get(body);
        } catch (NullPointerException ex) {
            return null;
        }

        request.setBody(body);

        return request;
    }

    private static HashMap<String, String> parseQuery(String queryString) throws UnsupportedEncodingException {
        HashMap<String, String> query = new HashMap<>();

        String[] queryParams = queryString.split("&");
        if (queryParams.length == 0) {
            return new HashMap<>();
        }

        for (String queryParam : queryParams) {
            String[] kv = queryParam.split("=");
            if (kv.length != 2) continue;
            String key = URLDecoder.decode(kv[0], "UTF-8");
            String value = URLDecoder.decode(kv[1], "UTF-8");
            query.put(key, value);
        }

        return query;
    }

    public HashMap<String, String> formData(char[] body) throws UnsupportedEncodingException {
        byte[] bytes = new String(body).getBytes(StandardCharsets.UTF_8);
        String parsedBody = new String(bytes, StandardCharsets.UTF_8);

        return Request.parseQuery(parsedBody);
    }

    private static String readLine(CharBuffer buffer) {
        if (!buffer.hasRemaining()) return null;

        int start = buffer.position();
        while (buffer.hasRemaining()) {
            char c = buffer.get();
            if (c == '\r' || c == '\n') {
                int end = buffer.position() - 1;
                if (c == '\r' && buffer.hasRemaining() && buffer.charAt(0) == '\n') {
                    buffer.get();
                }
                return extract(buffer, start, end);
            }
        }

        return extract(buffer, start, buffer.position());

    }

    private static String extract(CharBuffer buffer, int start, int end) {
        int pos = buffer.position();
        int lim = buffer.limit();

        buffer.limit(end);
        buffer.position(start);
        String line = buffer.toString();

        buffer.limit(lim);
        buffer.position(pos);
        return line;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method=" + method +
                ", uri=" + uri +
                ", version=" + version +
                ", headers=" + headers +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
