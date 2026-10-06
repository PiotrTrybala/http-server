package pl.piotrtrybala.http.request;

import pl.piotrtrybala.http.types.Method;
import pl.piotrtrybala.http.types.Version;

import java.net.URI;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashMap;

public class Request {

    private Method method;
    private URI uri;
    private Version version;

    public HashMap<String, String> headers;

    public char[] body;

    private Request(Method method, URI uri, Version version, HashMap<String, String> headers, char[] body) {
        this.method = method;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
        this.body = body;
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

    public static Request fromBuffer(char[] buffer) {

        if (buffer == null || buffer.length == 0) {
            return null;
        }

        CharBuffer charBuffer = CharBuffer.wrap(buffer);

        String requestLine = readLine(charBuffer);
        System.out.println("request line:" + requestLine);
        if (requestLine == null || requestLine.isEmpty()) return null;

        String[] parts = requestLine.split(" ");
        System.out.println(Arrays.toString(parts));
        if (parts.length < 3) return null;

        Method method;
        URI uri;
        Version version;

        try {
            method = Method.valueOf(parts[0].toUpperCase());
            uri = URI.create(parts[1]);
            version = Version.HTTP11; // support only for http/1.1

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        HashMap<String, String> headers = new HashMap<>();
        String headerLine;

        while((headerLine = readLine(charBuffer)) != null && !headerLine.isEmpty()) {
            int colorIndex = headerLine.indexOf(":");
            if (colorIndex != -1) {
                String headerName = headerLine.substring(0, colorIndex).trim();
                String headerValue = headerLine.substring(colorIndex + 1).trim();
                headers.put(headerName, headerValue);
            }
        }

        int contentLength = Integer.parseInt(headers.get("Content-Length"));
        char[] body = new char[contentLength];
        charBuffer.get(body);

        return new Request(method, uri, version, headers, body);
    }

    private static String readLine(CharBuffer buffer) {
        if (!buffer.hasRemaining()) return null;

        int start = buffer.position();
        while(buffer.hasRemaining()) {
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

}
