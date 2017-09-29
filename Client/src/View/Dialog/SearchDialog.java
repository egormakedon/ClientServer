package View.Dialog;

import View.Table;
import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowFocusListener;
import java.util.*;

public class SearchDialog {
    private Client client;
    private JFrame frame;
    private Table table;

    private JDialog searchDialog = new JDialog();
    private TemplateDialog templateDialog = new TemplateDialog(searchDialog);

    public void setClient(Client client) {
        this.client = client;

        table = new Table(client);
        table.setParentName("searchDialog");
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setDialog() {
        searchDialog.setTitle("Search");
        templateDialog.setDialog();
        templateDialog.getActionButt().setText("Search");

        SearchButtListener searchButtListener = new SearchButtListener();
        templateDialog.getActionButt().addActionListener(searchButtListener);

        CleanButtListener cleanButtListener = new CleanButtListener();
        templateDialog.getCleanButt().addActionListener(cleanButtListener);

        searchDialog.setSize(new Dimension(700, 700));
        searchDialog.setPreferredSize(new Dimension(700, 700));

        table.setTablePanel(new ArrayList());
        searchDialog.add(table.getTablePanel(), new GridBagConstraints(0, 7, 2, 1, 0.5,
                1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        WindowFocusListener windowFocusListener = searchDialog.getWindowFocusListeners()[0];
        searchDialog.removeWindowFocusListener(windowFocusListener);
    }

    class SearchButtListener implements ActionListener {

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

            ArrayList data = client.request(arrayList, "search", num);

            if (data.size() == 1 && (data.get(0).toString().equals("1") || data.get(0).toString().equals("2") || data.get(0).toString().equals("3"))) {
                table.setTablePanel(new ArrayList());

                if (data.get(0).toString().equals("1")) JOptionPane.showMessageDialog(frame, "Ошибка! Не все поля заполнены!");
                if (data.get(0).toString().equals("2")) JOptionPane.showMessageDialog(frame, "Ошибка! " +
                        "Поля Дом, Корпус и Квартира принимают целочисленные значения!");
                if (data.get(0).toString().equals("3")) JOptionPane.showMessageDialog(frame, "Данных не найдено!");

                return;
            }

            table.setTablePanel(data);
        }
    }

    class CleanButtListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField []textField = templateDialog.getTextField();
            textField[0].setText("");
            textField[1].setText("");

            client.clearSearchList();
            table.setTablePanel(new ArrayList());
        }
    }

    public JDialog getSearchDialog() {
        return searchDialog;
    }
}
