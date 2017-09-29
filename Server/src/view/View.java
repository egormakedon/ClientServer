package view;

import server.Server;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame = new JFrame("Server");
    private JButton run = new JButton("run server");
    private JButton stop = new JButton("stop server");
    private Server server;
    private JTextArea textArea = new JTextArea();

    public void setServer(Server server) {
        this.server = server;
    }

    public void setView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(92,160,250));
        frame.setSize(new Dimension(500,500));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(92,160,250));

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10,10,10,10);

        panel.add(run, c);

        c.gridx++;
        panel.add(stop, c);

        c.gridx = 0;
        frame.add(panel, c);

        textArea.setBackground(new Color(92,160,250));
        textArea.setForeground(Color.white);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(50,20,20,20);
        frame.add(textArea, c);

        RunStopServer runStopServer = new RunStopServer(server);
        runStopServer.setButtons(run, stop);
        runStopServer.setTextArea(textArea);
        runStopServer.setListeners();
    }

    public void show() {
        frame.setVisible(true);
    }
}