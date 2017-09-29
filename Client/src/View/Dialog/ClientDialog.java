package View.Dialog;

import View.Table;
import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientDialog {
    private Client client;

    private JDialog clientDialog = new JDialog();
    private JTextField ipTField = new JTextField(15);

    private Table table;

    public void setClient(Client client) {
        this.client = client;
    }
    public void setTable(Table table) {
        this.table = table;
    }

    public void setClientDialog() {
        clientDialog.setTitle("Client");
        clientDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        clientDialog.setLayout(new GridBagLayout());
        clientDialog.setResizable(false);
        clientDialog.setSize(new Dimension(300,500));
        clientDialog.setLocationRelativeTo(null);

        WindowFocus windowFocus = new WindowFocus(clientDialog);
        clientDialog.addWindowFocusListener(windowFocus);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel ipLabel = new JLabel("IP:");

        GridBagConstraints c = new GridBagConstraints();

        c.weighty = 0.5;
        c.weightx = 0.5;
        c.insets = new Insets(10,10,10,10);
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        panel.add(ipLabel, c);

        c.gridx++;
        panel.add(ipTField, c);

        JButton connectionButt = new JButton("connect");
        JButton disconnectionButt = new JButton("disconnect");

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        panel.add(connectionButt, c);

        c.gridy++;
        panel.add(disconnectionButt, c);

        connectionButt.setBackground(Color.yellow);
        disconnectionButt.setBackground(Color.yellow);

        ConnectionButtListener connectionButtListener = new ConnectionButtListener();
        connectionButt.addActionListener(connectionButtListener);

        DisconnectionButtListener disconnectionButtListener = new DisconnectionButtListener();
        disconnectionButt.addActionListener(disconnectionButtListener);

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new GridBagLayout());

        c = new GridBagConstraints();

        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;

        clientDialog.add(panel, c);

        c.insets = new Insets(150,0,0,0);
        clientDialog.add(logPanel, c);

        client.setLogPanel(logPanel, -1);
    }

    class ConnectionButtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            client.connectToServer(ipTField.getText());
        }
    }

    class DisconnectionButtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            client.disconnectFromServer();
            table.setTablePanel(new ArrayList<>());
        }
    }

    public JDialog getClientDialog() {
        return clientDialog;
    }
}
