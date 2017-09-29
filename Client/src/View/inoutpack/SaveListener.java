package View.inoutpack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import client.Client;

public class SaveListener implements ActionListener {
    private JFrame frame;
    private Client client;

    public SaveListener(JFrame frame, Client client) {
        this.frame = frame;
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog(frame, "Введите название файла для сохранения");
        String result = client.inRequest(fileName);
        JOptionPane.showMessageDialog(frame, result);
    }
}