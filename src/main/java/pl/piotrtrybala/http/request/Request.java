package pl.piotrtrybala.http.request;

import pl.piotrtrybala.http.types.Method;
import pl.piotrtrybala.http.types.Version;

import java.net.URI;
import java.nio.CharBuffer;
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

    public static Request fromBuffer(char[] buffer) {

        if (buffer == null || buffer.length == 0) {
            return null;
        }

        CharBuffer charBuffer = CharBuffer.wrap(buffer);

        String requestLine = readLine(charBuffer);
        if (requestLine == null || requestLine.isEmpty()) return null;

        String[] parts = requestLine.split(" ");
        if (parts.length < 3) return null;

        Method method;
        URI uri;
        Version version;

        try {
            method = Method.valueOf(parts[0].toUpperCase());
            uri = URI.create(parts[1]);
            version = Version.valueOf(parts[2].toUpperCase());
        } catch(Exception e) {
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

        char[] body = new char[charBuffer.remaining()];
        charBuffer.get(body);

        return new Request(method, uri, version, headers, body);
    }

    private static String readLine(CharBuffer buffer) {
        if (!buffer.hasRemaining()) return null;

        int start = buffer.position();

        while(buffer.hasRemaining()) {
            char c = buffer.get();

            if (c == '\n' || c == '\r') {
                int end = buffer.position() - 1;

                if (c == '\r' && buffer.hasRemaining() && buffer.charAt(0) == '\n') {
                    buffer.get();
                }
                return buffer.subSequence(start - buffer.position(), end - buffer.position()).toString();
            }
        }

        return buffer.subSequence(start - buffer.position(), buffer.limit() - buffer.position()).toString();
    }

}
