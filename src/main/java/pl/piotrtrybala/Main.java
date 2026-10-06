package pl.piotrtrybala;

import java.io.IOException;

public class Main {
    static void main(String[] args) {
        try {
            Server s = new Server(5000);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
