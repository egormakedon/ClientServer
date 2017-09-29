package Main;

import View.View;
import client.Client;

import javax.swing.*;

public class Main {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                View view = new View();
                view.setClient(client);
                view.show();
            }
        });
    }
}