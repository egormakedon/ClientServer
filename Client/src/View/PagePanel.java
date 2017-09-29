package View;

import Model.*;
import client.Client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PagePanel {
    private Client client;
    private String parentName = "";

    private JPanel pagePanel = new JPanel();
    private JPanel dataPanel;

    private JButton firstPageButt = new JButton(new ImageIcon("images/first.png"));
    private JButton prevPageButt = new JButton(new ImageIcon("images/prev.png"));
    private JButton nextPageButt = new JButton(new ImageIcon("images/next.png"));
    private JButton lastPageButt = new JButton(new ImageIcon("images/last.png"));

    private JTextField numberRecordsField = new JTextField(11);
    private JLabel currPage = new JLabel("1");
    private JLabel totalPages = new JLabel("1");
    private JLabel totalRecords = new JLabel("0");
    private JLabel currRecords = new JLabel("0");

    public PagePanel(Client client, String string) {
        this.client = client;
        parentName = string;
    }

    public void run(JPanel dataPanel) {
        this.dataPanel = dataPanel;

        numberRecordsField.setText("10");
        currPage.setText("1");
        totalPages.setText("1");
        totalRecords.setText("0");
        currRecords.setText("0");

        setDataInfo();
        setPagePanel();
        setListeners();
    }

    private void setDataInfo() {
        int totRec = client.getTotalRecords(parentName);

        if (totRec == 0) {
            numberRecordsField.setText("10");
            currPage.setText("1");
            totalPages.setText("1");
            totalRecords.setText("0");
            currRecords.setText("0");

            return;
        }

        totalRecords.setText(Integer.toString(totRec));

        int numberRecords = Integer.parseInt(numberRecordsField.getText());
        if (totRec % numberRecords == 0) totalPages.setText(Integer.toString(totRec / numberRecords));
        else {
            double x = totRec;
            x = x / numberRecords;
            totalPages.setText(Integer.toString((int) Math.floor(x) + 1));
        }

        if (totRec > numberRecords) currRecords.setText(Integer.toString(numberRecords));
        else currRecords.setText(Integer.toString(totRec));
    }

    private void setPagePanel() {
        firstPageButt.setBackground(Color.yellow);
        prevPageButt.setBackground(Color.yellow);
        nextPageButt.setBackground(Color.yellow);
        lastPageButt.setBackground(Color.yellow);

        pagePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 7, 0, 7);
        pagePanel.add(numberRecordsField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        pagePanel.add(currRecords, c);

        c.gridx++;
        pagePanel.add(totalRecords, c);

        c.gridx++;
        pagePanel.add(currPage, c);

        c.gridx++;
        pagePanel.add(firstPageButt, c);

        c.gridx++;
        pagePanel.add(prevPageButt, c);

        c.gridx++;
        pagePanel.add(nextPageButt, c);

        c.gridx++;
        pagePanel.add(lastPageButt, c);

        c.gridx++;
        pagePanel.add(totalPages, c);
    }

    private void setListeners() {
        FirstPageListener firstPageListener = new FirstPageListener();
        PrevPageListener prevPageListener = new PrevPageListener();
        NextPageListener nextPageListener = new NextPageListener();
        LastPageListener lastPageListener = new LastPageListener();

        firstPageButt.addActionListener(firstPageListener);
        prevPageButt.addActionListener(prevPageListener);
        nextPageButt.addActionListener(nextPageListener);
        lastPageButt.addActionListener(lastPageListener);

        FieldListeners fieldListeners = new FieldListeners();
        fieldListeners.setFieldListeners();
    }

    private void updateDataPanel(ArrayList<Person> arrayList) {
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

        dataPanel.revalidate();
    }

    private void addLabels(ArrayList<String> list, GridBagConstraints c) {
        JLabel[] label = new JLabel[8];

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

    class FirstPageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currPage.getText().equals("1")) return;

            currPage.setText("1");
            currRecords.setText(numberRecordsField.getText());

            int firstIndex = (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText());
            int lastIndex = Integer.parseInt(numberRecordsField.getText()) + firstIndex;

            updateDataPanel(client.getListOfData(firstIndex, lastIndex, parentName));
        }
    }

    class PrevPageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currPage.getText().equals("1")) return;

            currPage.setText(Integer.toString(Integer.parseInt(currPage.getText()) - 1));
            currRecords.setText(numberRecordsField.getText());

            int firstIndex = (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText());
            int lastIndex = Integer.parseInt(numberRecordsField.getText()) + firstIndex;

            updateDataPanel(client.getListOfData(firstIndex, lastIndex, parentName));
        }
    }

    class NextPageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currPage.getText().equals(totalPages.getText())) return;

            currPage.setText(Integer.toString(Integer.parseInt(currPage.getText()) + 1));

            int totRec = client.getTotalRecords(parentName);

            if (totRec - (Integer.parseInt(currPage.getText())) * Integer.parseInt(numberRecordsField.getText()) >= 0) {
                currRecords.setText(numberRecordsField.getText());
            }

            else {
                currRecords.setText(Integer.toString(totRec - (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText())));
            }

            int firstIndex = (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText());
            int lastIndex = Integer.parseInt(currRecords.getText()) + firstIndex;

            updateDataPanel(client.getListOfData(firstIndex, lastIndex, parentName));
        }
    }

    class LastPageListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currPage.getText().equals(totalPages.getText())) return;

            currPage.setText(totalPages.getText());

            int totRec = client.getTotalRecords(parentName);

            if (totRec - (Integer.parseInt(currPage.getText())) * Integer.parseInt(numberRecordsField.getText()) >= 0) {
                currRecords.setText(numberRecordsField.getText());
            }

            else {
                currRecords.setText(Integer.toString(totRec - (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText())));
            }

            int firstIndex = (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText());
            int lastIndex = Integer.parseInt(currRecords.getText()) + firstIndex;

            updateDataPanel(client.getListOfData(firstIndex, lastIndex, parentName));
        }
    }

    class FieldListeners {
        public void setFieldListeners() {
            numberRecordsField.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    numberRecordsField.setFocusable(true);
                    numberRecordsField.requestFocus();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    numberRecordsField.setFocusable(true);
                    numberRecordsField.requestFocus();
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            numberRecordsField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                        numberRecordsField.setFocusable(false);
                    }
                }
            });

            numberRecordsField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                }

                @Override
                public void focusLost(FocusEvent e) {
                    String string = numberRecordsField.getText();

                    if (string.equals("")) {
                        wrondParametr();
                    }

                    if (!checkString(string)) {
                        wrondParametr();
                    }

                    if (checkString(string)) {
                        if (Integer.parseInt(string) > 10 || Integer.parseInt(string) < 1) {
                            wrondParametr();
                        }

                        else {
                            currPage.setText("1");
                            setDataInfo();

                            int firstIndex = (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText());
                            int lastIndex = Integer.parseInt(currRecords.getText()) + firstIndex;

                            updateDataPanel(client.getListOfData(firstIndex, lastIndex, parentName));
                        }
                    }
                }
            });
        }

        private void wrondParametr() {
            numberRecordsField.setText("10");
            currPage.setText("1");
            totalPages.setText("1");
            totalRecords.setText("0");
            currRecords.setText("0");
            setDataInfo();

            int firstIndex = (Integer.parseInt(currPage.getText()) - 1) * Integer.parseInt(numberRecordsField.getText());
            int lastIndex = Integer.parseInt(currRecords.getText()) + firstIndex;

            updateDataPanel(client.getListOfData(firstIndex, lastIndex, parentName));

            JOptionPane.showMessageDialog(pagePanel, "Ошибка! Параметр принимает число от 1 до 10!");

            return;
        }
    }

    private boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public JPanel getPagePanel() {
        return pagePanel;
    }
}