package view;

import server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunStopServer {
    private Server server;
    private JButton runButton;
    private JButton stopButton;
    private Thread thread = new Thread();
    private boolean isRunning = false;
    private JTextArea textArea;

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public RunStopServer(Server server) {
        this.server = server;
    }

    public void setButtons(JButton runButton, JButton stopButton) {
        this.runButton = runButton;
        this.stopButton = stopButton;
    }

    public void setListeners() {
        RunListener runListener = new RunListener();
        StopListener stopListener = new StopListener();

        runButton.addActionListener(runListener);
        stopButton.addActionListener(stopListener);
    }

    class RunListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isRunning) {
                isRunning = true;
                server = new Server();
                server.setServerSocket();
                server.setTextArea(textArea);
                thread = new Thread(server);
                thread.start();

                textArea.append("run server\n");
            }
        }
    }

    class StopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isRunning) {
                isRunning = false;
                server.closeSockets();
                thread.interrupt();

                textArea.append("stop server\n");
            }
        }
    }

}
