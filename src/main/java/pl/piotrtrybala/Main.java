package pl.piotrtrybala;

import pl.piotrtrybala.http.Config;
import pl.piotrtrybala.http.Server;

import java.io.IOException;

public class Main {
    static void main(String[] args) {
        Config httpConfig = new Config();

        try {
            Server server = new Server(httpConfig);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
