package pl.piotrtrybala;

public class ResponseBody {
    private ResponseBodyType type;
    private String data;

    public ResponseBody(ResponseBodyType type, String data) {
        this.type = type;
        this.data = data;
    }

    public ResponseBodyType getType() {
        return type;
    }

    public void setType(ResponseBodyType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
