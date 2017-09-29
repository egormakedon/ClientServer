package View.inoutpack;

import View.*;
import client.Client;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OpenListener implements ActionListener {
    private JFrame frame;
    private Table table;
    private Client client;

    public OpenListener(JFrame frame, Table table, Client client) {
        this.frame = frame;
        this.table = table;
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog(frame, "Введите название файла для открытия");
        ArrayList result = client.outRequest(fileName);
        table.setTablePanel(result);
    }
}