package pl.piotrtrybala;

public class ServerConfig {
    public final short port;
    public final int maxRequestSize;
    ServerConfig(short port, int maxRequestSize) {
        this.port = port;
        this.maxRequestSize = maxRequestSize;
    }
}
