package main;

import server.Server;
import view.View;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        View view = new View();
        view.setServer(server);
        view.setView();
        view.show();
    }
}