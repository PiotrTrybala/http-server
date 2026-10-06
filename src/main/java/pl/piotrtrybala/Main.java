package pl.piotrtrybala;

import java.io.IOException;

public class Main {
    static void main(String[] args) {
        Server s = new Server(new ServerConfig((short) 10000));
    }
}
