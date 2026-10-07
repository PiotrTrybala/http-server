package pl.piotrtrybala.http.types;

public enum StatusCode {

    OK(200, "OK"),

    CREATED(201,"Created"),

    BAD_REQUEST(400,"Bad Request"),

    UNAUTHORIZED(401,"Unauthorized"),

    FORBIDDEN(403,"Forbidden"),

    NOT_FOUND(404,"Not found"),

    INTERNAL_SERVER_ERROR(500,"Internal server error"),

    NOT_IMPLEMENTED(501,"Not Implemented"),

    SERVICE_UNAVAILABLE(503, "Server unavailable");

    private int status;
    private String message;

    StatusCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return status + " " + message;
    }
}
