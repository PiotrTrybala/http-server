package pl.piotrtrybala.http;

public class Config {
    public short port = 12354; // default port
    public int maxRequestSize = 16384; // default 16kb

    public Config(short port, int maxRequestSize) {
        this.port = port;
        this.maxRequestSize = maxRequestSize;
    }

    public int getMaxRequestSize() {
        return maxRequestSize;
    }

    public short getPort() {
        return port;
    }
}
