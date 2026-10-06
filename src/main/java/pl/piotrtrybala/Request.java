//package pl.piotrtrybala;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//
//public class Request {
//
//    // request line
//    private HTTPMethod method;
//    private URI uri;
//    private HTTPVersion version;
//
//    // headers
//    private HashMap<String, String> headers;
//
//    // body
//    private char[] body;
//
//    public Request() {}
//
//    public HTTPMethod getMethod() {
//        return method;
//    }
//
//    public void setMethod(HTTPMethod method) {
//        this.method = method;
//    }
//
//    public URI getUri() {
//        return uri;
//    }
//
//    public void setUrl(URI uri) {
//        this.uri = uri;
//    }
//
//    public HTTPVersion getVersion() {
//        return version;
//    }
//
//    public void setVersion(HTTPVersion version) {
//        this.version = version;
//    }
//
//    public HashMap<String, String> getHeaders() {
//        return headers;
//    }
//
//    public void setHeaders(HashMap<String, String> headers) {
//        this.headers = headers;
//    }
//
//    public String getHeader(String header) {
//        return this.headers.get(header);
//    }
//
//    public char[] getBody() {
//        return body;
//    }
//
//    public void setBody(char[] body) {
//        this.body = body;
//    }
//
//    public static Request parse(List<String> headers, char[] requestBody) throws Exception {
//        Request request = new Request();
//        // parse request line
//
//        if (headers.size() == 1) {
//            // error: invalid request header
//        }
//
//        String requestLine = headers.get(0);
//
//        String[] parts = requestLine.split(" ");
//        if (parts.length != 3) {
//            // error: invalid request header line
//        }
//
//        // request method
//        request.method = Enum.valueOf(HTTPMethod.class, parts[0]); // error: invalid method type
//        request.uri = new URI(parts[1]); // error: invalid uri
//        request.version = HTTPVersion.HTTP11; // default, only this version supported
//
//        for (int i = 1; i < headers.size(); i++) {
//            String[] headerParts = headers.get(i).split(":", 1);
//            request.headers.put(headerParts[0], headerParts[1]);
//        }
//
//        String rawContentType = request.headers.get("content-type");
//        HTTPContentType contentType = Enum.valueOf(HTTPContentType.class, rawContentType); // error: invalid contentType
//
//        switch(contentType) {
//            case
//        }
//
//        // parse headers
//
//        return null;
//    }
//}
