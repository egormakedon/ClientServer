package client;

import org.apache.log4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    private Socket socket = new Socket();
    private InputStream sin;
    private OutputStream sout;
    private DataOutputStream dataOut;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private boolean connection = false;
    private JPanel logPanel;
    private int logKol;
    private static final Logger log = Logger.getLogger(Client.class);

    public void setLogPanel(JPanel logPanel, int logKol) {
        this.logPanel = logPanel;
        this.logKol = logKol;
    }

    public void connectToServer(String ip) {
        if (connection) return;

        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            socket = new Socket(ipAddress, 7777);

            connection = true;
            printLog("Соединение установлено");

            sout = socket.getOutputStream();
            sin = socket.getInputStream();

            dataOut = new DataOutputStream(sout);
            objOut = new ObjectOutputStream(sout);
            objIn = new ObjectInputStream(sin);

        } catch (UnknownHostException e1) {
            log.error(e1.getMessage());
        } catch (IOException e1) {
            if (!connection || socket.isClosed()) printLog("Соединение не установлено");
            log.error(e1.getMessage());
        }
    }

    public void disconnectFromServer() {
        try {
            if (connection) {
                objIn.close();
                dataOut.close();
                objOut.close();

                sin.close();
                sout.close();

                socket.close();

                connection = false;
                printLog("Клиент отсоединен");
            }

        } catch (IOException e) {
            try {
                sin.close();
                sout.close();

                socket.close();

                connection = false;
                printLog("Клиент отсоединен");
            } catch (IOException e1) {
                log.error(e1.getMessage());
            }
            log.error(e.getMessage());
        }
    }

    public ArrayList request(ArrayList<String> arrayList, String string, int num) {
        if (!connection) return new ArrayList();

        ArrayList newList = new ArrayList();

        try {
            dataOut.writeUTF(string);
            dataOut.flush();

            objOut.writeObject(arrayList);
            objOut.flush();

            if (string.equals("search") || string.equals("erase")) {
                dataOut.writeInt(num);
                dataOut.flush();
            }

            newList = (ArrayList) objIn.readObject();
            return newList;

        } catch (IOException e) {
            disconnectFromServer();
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        return newList;
    }

    public void clearSearchList() {
        String string = "clearSearchDialog";

        if (!connection) return;

        try {
            dataOut.writeUTF(string);
            dataOut.flush();

        } catch (IOException e) {
            disconnectFromServer();
            log.error(e.getMessage());
        }
    }

    public int getTotalRecords(String parentName) {
        int totRec = 0;

        if (!connection) return 0;

        try {
            dataOut.writeUTF(parentName);
            dataOut.flush();

            totRec = objIn.readInt();

        } catch (IOException e) {
            disconnectFromServer();
            log.error(e.getMessage());
        }

        return totRec;
    }

    public ArrayList getListOfData(int firstIndex, int lastIndex, String parentName) {
        String string = "pagepanel";
        ArrayList arrayList = new ArrayList();

        if (!connection) return new ArrayList();

        try {
            dataOut.writeUTF(string);
            dataOut.flush();

            dataOut.writeInt(firstIndex);
            dataOut.flush();

            dataOut.writeInt(lastIndex);
            dataOut.flush();

            dataOut.writeUTF(parentName);
            dataOut.flush();

            arrayList = (ArrayList) objIn.readObject();

        } catch (IOException e) {
            disconnectFromServer();
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        return arrayList;
    }

    public String inRequest(String fileName) {
        String string = "save";
        String result = "Файл не сохранен";

        if (!connection) return result;

        try {
            dataOut.writeUTF(string);
            dataOut.flush();

            dataOut.writeUTF(fileName);
            dataOut.flush();

            result = objIn.readUTF();
            return result;

        } catch (IOException e) {
            disconnectFromServer();
            log.error(e.getMessage());
        }

        return result;
    }

    public ArrayList outRequest(String fileName) {
        String string = "open";
        ArrayList arrayList = new ArrayList();

        if (!connection) return arrayList;

        try {
            dataOut.writeUTF(string);
            dataOut.flush();

            dataOut.writeUTF(fileName);
            dataOut.flush();

            arrayList = (ArrayList) objIn.readObject();
            return arrayList;

        } catch (IOException e) {
            disconnectFromServer();
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        return arrayList;
    }

    private void printLog(String string) {
        JLabel label = new JLabel(string);

        GridBagConstraints c = new GridBagConstraints();

        c.weighty = 0.5;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = ++logKol;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;

        logPanel.add(label, c);
        logPanel.revalidate();
    }
}