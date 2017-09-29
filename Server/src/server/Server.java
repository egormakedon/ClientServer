package server;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket socket;
    private ArrayList<ClientController> arrayList = new ArrayList<>();
    private JTextArea textArea;
    private static final Logger log = Logger.getLogger(Server.class);

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void setServerSocket() {
        try {
            serverSocket = new ServerSocket(7777);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                socket = serverSocket.accept();

                textArea.append("client connected\n");

                ClientController clientController = new ClientController(socket);
                Thread thread = new Thread(clientController);
                clientController.setThread(thread);
                clientController.setTextArea(textArea);

                arrayList.add(clientController);
                thread.start();

            } catch (IOException e) {
                log.error(e.getMessage());
                break;
            }
        }
    }

    public void closeSockets() {
        for (ClientController clientController : arrayList) {
            if (!clientController.getSocket().isClosed()) clientController.closeSocket();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}