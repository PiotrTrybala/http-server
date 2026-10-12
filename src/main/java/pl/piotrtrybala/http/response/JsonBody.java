package pl.piotrtrybala.http.response;

public class JsonBody implements Body {

    private final String body;
    public JsonBody(String body) {
        this.body = body;
    }

    public String getContent() {
        return this.body;
    }

    public int size() {
        return this.body.length();
    }
}
