package View;

import Model.Person;
import client.Client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class Table {
    private JPanel tablePanel = new JPanel();
    private JPanel dataPanel = new JPanel();
    private Client client;

    private String parentName = "";

    public Table(Client client) {
        tablePanel.setLayout(new GridBagLayout());
        dataPanel.setLayout(new GridBagLayout());

        this.client = client;
    }

    public void setParentName(String string) {
        parentName = string;
    }

    public void setTablePanel(ArrayList<Person> arrayList) {
        tablePanel.removeAll();
        dataPanel.removeAll();

        Border border = BorderFactory.createLineBorder(Color.black);

        JLabel[] personLabels = new JLabel[9];
        for (int i = 0; i < 9; i++) {
            personLabels[i] = new JLabel();

            personLabels[i].setBorder(border);
            personLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            personLabels[i].setVerticalAlignment(SwingConstants.CENTER);
        }

        personLabels[0].setText("<html><center>фио<br>студента</html>");
        personLabels[1].setText("адрес прописки");
        personLabels[2].setText("страна");
        personLabels[3].setText("область");
        personLabels[4].setText("город");
        personLabels[5].setText("улица");
        personLabels[6].setText("дом");
        personLabels[7].setText("корпус");
        personLabels[8].setText("квартира");

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 0.5;
        c.ipady = 20;
        c.fill = GridBagConstraints.HORIZONTAL;

        dataPanel.add(personLabels[0], c);

        c.gridx++;
        c.ipady = 9;
        c.gridheight = 1;
        c.gridwidth = 7;

        dataPanel.add(personLabels[1], c);

        c.gridy++;
        c.gridwidth = 1;

        dataPanel.add(personLabels[2], c);

        for (int i = 3; i < 9; i++) {
            c.gridx++;
            dataPanel.add(personLabels[i], c);
        }

        if (arrayList.size() == 0) {
            PagePanel pagePanel = new PagePanel(client, parentName);
            pagePanel.run(dataPanel);

            JPanel panel = new JPanel();
            panel.add(pagePanel.getPagePanel(), BorderLayout.SOUTH);

            c.gridx = 0;
            c.gridy++;
            c.anchor = GridBagConstraints.NORTH;
            c.gridwidth = 10;

            tablePanel.add(dataPanel, c);
            c.gridy++;
            tablePanel.add(panel, c);

            tablePanel.revalidate();

            return;
        }

        for (Person person : arrayList) {
            ArrayList<String> list = new ArrayList();

            list.add(person.getSurname() + " " + person.getName().charAt(0) + "." + person.getFathername().charAt(0) + ".");
            list.add(person.getCountry());
            list.add(person.getRegion());
            list.add(person.getCity());
            list.add(person.getStreet());
            list.add(Integer.toString(person.getHouse()));
            list.add(Integer.toString(person.getHousing()));
            list.add(Integer.toString(person.getApartment()));

            c.gridy++;
            addLabels(list, c);
        }

        PagePanel pagePanel = new PagePanel(client, parentName);
        pagePanel.run(dataPanel);

        JPanel panel = new JPanel();
        panel.add(pagePanel.getPagePanel(), BorderLayout.SOUTH);

        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.NORTH;
        c.gridwidth = 10;

        tablePanel.add(dataPanel, c);
        c.gridy++;
        tablePanel.add(panel, c);

        tablePanel.revalidate();
    }

    private void addLabels(ArrayList<String> list, GridBagConstraints c) {
        JLabel []label = new JLabel[8];

        Border border = BorderFactory.createLineBorder(Color.black);

        c.gridx = -1;
        for (int i = 0; i < 8; i++) {
            label[i] = new JLabel();

            label[i].setBorder(border);
            label[i].setHorizontalAlignment(SwingConstants.CENTER);
            label[i].setVerticalAlignment(SwingConstants.CENTER);
            label[i].setText(list.get(i));

            c.gridx++;
            dataPanel.add(label[i], c);
        }
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }
}