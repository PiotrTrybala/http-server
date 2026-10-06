package pl.piotrtrybala;

public enum HTTPVersion {
    HTTP11("HTTP/1.1");

    private String version;

    public String getVersion() {
        return this.version;
    }

    HTTPVersion(String version) {
        this.version = version;
    }
}
