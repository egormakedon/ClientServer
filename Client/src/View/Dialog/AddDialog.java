package View.Dialog;

import View.Table;
import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddDialog {
    private Client client;
    private JFrame frame;
    private Table table;

    private JDialog addDialog = new JDialog();

    private JTextField []textField = new JTextField[10];
    private JButton addButt = new JButton("Add");
    private JButton cleanButt = new JButton("Clean");

    public AddDialog() {
        for (int i = 0; i < 10; i++) {
            textField[i] = new JTextField();
            textField[i].setColumns(15);
        }

        addButt.setBackground(Color.yellow);
        cleanButt.setBackground(Color.yellow);
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    public void setTable(Table table) {
        this.table = table;
    }

    public void setDialog() {
        addDialog.setTitle("Add");
        addDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        addDialog.getContentPane().setBackground(Color.gray);
        addDialog.setLayout(new GridBagLayout());
        addDialog.setResizable(false);

        JLabel []label = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            label[i] = new JLabel();
        }

        label[0].setText("фамилия");
        label[1].setText("имя");
        label[2].setText("отчество");
        label[3].setText("страна");
        label[4].setText("область");
        label[5].setText("город");
        label[6].setText("улица");
        label[7].setText("дом");
        label[8].setText("корпус");
        label[9].setText("квартира");

        for (int i = 0; i < 10; i++) {
            label[i].setForeground(Color.yellow);
        }

        GridBagConstraints c = new GridBagConstraints();

        c.gridy = -1;
        c.insets = new Insets(10, 10, 10, 10);

        for (int i = 0; i < 10; i++) {
            c.gridy++;
            c.gridx = 0;
            addDialog.add(label[i], c);
            c.gridx++;
            addDialog.add(textField[i], c);
        }

        c.gridx = 0;
        c.gridy++;
        addDialog.add(addButt, c);
        c.gridx++;
        addDialog.add(cleanButt, c);

        addDialog.pack();

        WindowFocus windowFocus = new WindowFocus(addDialog);
        addDialog.addWindowFocusListener(windowFocus);

        AddButtListener addButtListener = new AddButtListener();
        addButt.addActionListener(addButtListener);

        CleanButtListener cleanButtListener = new CleanButtListener();
        cleanButt.addActionListener(cleanButtListener);
    }

    class AddButtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> arrayList = new ArrayList();

            for (int i = 0; i < 10; i++) {
                arrayList.add(textField[i].getText());
            }

            ArrayList data = client.request(arrayList, "add", -1);

            addDialog.setVisible(false);
            if (data.size() == 1 && (data.get(0).toString().equals("1") || data.get(0).toString().equals("2"))) {
                if (data.get(0).toString().equals("1")) JOptionPane.showMessageDialog(frame, "Ошибка! Не все поля заполнены!");
                if (data.get(0).toString().equals("2")) JOptionPane.showMessageDialog(frame, "Ошибка! " +
                        "Поля Дом, Корпус и Квартира принимают целочисленные значения!");

                return;
            }

            table.setTablePanel(data);
        }
    }

    class CleanButtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 10; i++) {
                textField[i].setText("");
            }
        }
    }

    public JDialog getAddDialog() {
        return addDialog;
    }
}