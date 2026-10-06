package pl.piotrtrybala;

public enum HTTPContentType {
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_WEBP("image/webp"),
    APPLICATION_OCTET_STREAM("application/octet_stream"),
    APPLICATION_JSON("application/json"),
    APPLICATION_FORM_DATA("application/x-www-form-data"),
    APPLICATION_MULTIPART("multipart/form-data"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain");

    private final String contentType;

    HTTPContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
