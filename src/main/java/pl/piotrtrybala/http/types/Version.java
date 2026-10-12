package pl.piotrtrybala.http.types;

public enum Version {
    HTTP11("HTTP/1.1");

    private String version;

    public String getVersion() {
        return this.version;
    }

    Version(String version) {
        this.version = version;
    }
}
