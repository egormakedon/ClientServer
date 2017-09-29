package View.Dialog;

import View.Table;
import client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EraseDialog {
    private Client client;
    private JFrame frame;
    private Table table;

    private JDialog eraseDialog = new JDialog();
    private TemplateDialog templateDialog = new TemplateDialog(eraseDialog);

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
        eraseDialog.setTitle("Erase");
        templateDialog.setDialog();
        templateDialog.getActionButt().setText("Erase");

        EraseButtListener eraseButtListener = new EraseButtListener();
        templateDialog.getActionButt().addActionListener(eraseButtListener);

        CleanButtListener cleanButtListener = new CleanButtListener();
        templateDialog.getCleanButt().addActionListener(cleanButtListener);
    }

    class EraseButtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton []radioButt = templateDialog.getRadioButt();
            JTextField []textField = templateDialog.getTextField();

            ArrayList<String> arrayList = new ArrayList();

            int num = 1;
            for (int i = 0; i < 3; i++) {
                if (i < 2) arrayList.add(textField[i].getText());

                if (radioButt[i].isSelected()) num = i + 1;
            }

            ArrayList data = client.request(arrayList, "erase", num);

            eraseDialog.setVisible(false);
            if (data.size() == 1 && (data.get(0).toString().equals("1") || data.get(0).toString().equals("2") || data.get(0).toString().equals("3"))) {
                if (data.get(0).toString().equals("1")) JOptionPane.showMessageDialog(frame, "Ошибка! Не все поля заполнены!");
                if (data.get(0).toString().equals("2")) JOptionPane.showMessageDialog(frame, "Ошибка! " +
                        "Поля Дом, Корпус и Квартира принимают целочисленные значения!");
                if (data.get(0).toString().equals("3")) JOptionPane.showMessageDialog(frame, "Данных не найдено!");

                return;
            }

            String kol = new String();

            if (data.get(0).toString().equals("-1")) {
                kol = data.get(1).toString();
                data.clear();
            }

            else {
                kol = data.get(data.size() - 1).toString();
                data.remove(data.size() - 1);
            }

            table.setTablePanel(data);

            JOptionPane.showMessageDialog(frame, "Удалено данных: " + kol);
        }
    }

    class CleanButtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField []textField = templateDialog.getTextField();
            textField[0].setText("");
            textField[1].setText("");
        }
    }

    public JDialog getEraseDialog() {
        return eraseDialog;
    }
}
