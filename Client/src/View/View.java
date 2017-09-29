package View;

import View.Dialog.AddDialog;
import View.Dialog.ClientDialog;
import View.Dialog.EraseDialog;
import View.Dialog.SearchDialog;
import View.inoutpack.OpenListener;
import View.inoutpack.SaveListener;
import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View {
    private Table table;
    private Client client;

    private AddDialog addDialog = new AddDialog();
    private SearchDialog searchDialog = new SearchDialog();
    private EraseDialog eraseDialog = new EraseDialog();

    private ClientDialog clientDialog = new ClientDialog();

    private JFrame frame = new JFrame("Second Lab");

    private JMenuItem openMItem = new JMenuItem("Open");
    private JMenuItem saveMItem = new JMenuItem("Save");
    private JMenuItem addMItem = new JMenuItem("Add");
    private JMenuItem searchMItem = new JMenuItem("Search");
    private JMenuItem eraseMItem = new JMenuItem("Erase");

    private JButton openButt = new JButton("Open");
    private JButton saveButt = new JButton("Save");
    private JButton addButt = new JButton("Add");
    private JButton searchButt = new JButton("Search");
    private JButton eraseButt = new JButton("Erase");

    private JMenuItem clientMItem = new JMenuItem("client");
    private JButton clientButt = new JButton("client");

    public void setClient(Client client) {
        this.client = client;
        table = new Table(client);
        table.setParentName("frame");
    }

    public void show() {
        setFrame();
        setBar();
        setDialogs();
        setListeners();
        addTablePanel();

        frame.setVisible(true);
    }

    private void setFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(1366, 768));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
    }

    private void setBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JPanel instrumentsPanel = new JPanel();

        menuBar.setSize(new Dimension(0, 27));
        menuBar.setPreferredSize(new Dimension(0, 27));
        menuBar.setBackground(Color.yellow);

        menu.setIcon(new ImageIcon("images/menu.png"));
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        instrumentsPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        instrumentsPanel.setBackground(Color.darkGray);
        instrumentsPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 20;
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        frame.add(instrumentsPanel, c);

        setMItems(menu);
        setButtons(instrumentsPanel);
    }

    private void setMItems(JMenu menu) {
        openMItem.setIcon(new ImageIcon("images/open.png"));
        saveMItem.setIcon(new ImageIcon("images/save.png"));
        addMItem.setIcon(new ImageIcon("images/add.png"));
        searchMItem.setIcon(new ImageIcon("images/search.png"));
        eraseMItem.setIcon(new ImageIcon("images/erase.png"));
        clientMItem.setIcon(new ImageIcon("images/client.png"));

        openMItem.setBackground(Color.yellow);
        saveMItem.setBackground(Color.yellow);
        addMItem.setBackground(Color.yellow);
        searchMItem.setBackground(Color.yellow);
        eraseMItem.setBackground(Color.yellow);
        clientMItem.setBackground(Color.yellow);

        menu.add(openMItem);
        menu.add(saveMItem);
        menu.addSeparator();
        menu.add(addMItem);
        menu.add(searchMItem);
        menu.add(eraseMItem);
        menu.addSeparator();
        menu.add(clientMItem);
    }

    private void setButtons(JPanel instrumentsPanel) {
        openButt.setIcon(new ImageIcon("images/open.png"));
        saveButt.setIcon(new ImageIcon("images/save.png"));
        addButt.setIcon(new ImageIcon("images/add.png"));
        searchButt.setIcon(new ImageIcon("images/search.png"));
        eraseButt.setIcon(new ImageIcon("images/erase.png"));
        clientButt.setIcon(new ImageIcon("images/client.png"));

        openButt.setBackground(Color.yellow);
        saveButt.setBackground(Color.yellow);
        addButt.setBackground(Color.yellow);
        searchButt.setBackground(Color.yellow);
        eraseButt.setBackground(Color.yellow);
        clientButt.setBackground(Color.yellow);

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 100);

        instrumentsPanel.add(openButt, c);

        c.gridx++;
        instrumentsPanel.add(saveButt, c);

        c.gridx++;
        instrumentsPanel.add(addButt, c);

        c.gridx++;
        instrumentsPanel.add(searchButt, c);

        c.gridx++;
        instrumentsPanel.add(eraseButt, c);

        c.gridx++;
        instrumentsPanel.add(clientButt, c);
    }

    private void setDialogs() {
        clientDialog.setClient(client);
        clientDialog.setClientDialog();
        clientDialog.setTable(table);

        addDialog.setDialog();
        addDialog.setClient(client);
        addDialog.setFrame(frame);
        addDialog.setTable(table);

        searchDialog.setClient(client);
        searchDialog.setDialog();
        searchDialog.setFrame(frame);

        eraseDialog.setDialog();
        eraseDialog.setClient(client);
        eraseDialog.setFrame(frame);
        eraseDialog.setTable(table);
    }

    private void setListeners() {
        OpenListener openListener = new OpenListener(frame, table, client);
        SaveListener saveListener = new SaveListener(frame, client);

        AddListener addListener = new AddListener();
        SearchListener searchListener = new SearchListener();
        EraseListener eraseListener = new EraseListener();

        openButt.addActionListener(openListener);
        openMItem.addActionListener(openListener);
        saveButt.addActionListener(saveListener);
        saveMItem.addActionListener(saveListener);
        addButt.addActionListener(addListener);
        addMItem.addActionListener(addListener);
        searchButt.addActionListener(searchListener);
        searchMItem.addActionListener(searchListener);
        eraseButt.addActionListener(eraseListener);
        eraseMItem.addActionListener(eraseListener);

        ClientListener clientListener = new ClientListener();
        clientMItem.addActionListener(clientListener);
        clientButt.addActionListener(clientListener);
    }

    private void addTablePanel() {
        table.setTablePanel(new ArrayList());

        frame.add(table.getTablePanel(), new GridBagConstraints(0, 0, 1, 1, 0.5,
                1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(60, 0, 0, 0), 0, 0));
    }

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addDialog.getAddDialog().setLocationRelativeTo(null);
            addDialog.getAddDialog().setVisible(true);
        }
    }

    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            searchDialog.getSearchDialog().setLocationRelativeTo(null);
            searchDialog.getSearchDialog().setVisible(true);
        }
    }

    class EraseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            eraseDialog.getEraseDialog().setLocationRelativeTo(null);
            eraseDialog.getEraseDialog().setVisible(true);
        }
    }

    class ClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            clientDialog.getClientDialog().setLocationRelativeTo(null);
            clientDialog.getClientDialog().setVisible(true);
        }
    }
}