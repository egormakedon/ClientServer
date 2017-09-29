package server;

import Controller.Controller;
import Model.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController implements Runnable {
    private Socket socket;
    private OutputStream sout;
    private InputStream sin;
    private DataInputStream dataIn;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Model model = new Model();
    private Controller controller = new Controller();
    private Thread thread;
    private JTextArea textArea;
    private static final Logger log = Logger.getLogger(ClientController.class);

    public void setTextArea(JTextArea textArea) { this.textArea = textArea; }
    public ClientController(Socket socket) {
        this.socket = socket;
    }
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            controller.setModel(model);

            sout = socket.getOutputStream();
            sin = socket.getInputStream();

            dataIn = new DataInputStream(sin);
            objOut = new ObjectOutputStream(sout);
            objIn = new ObjectInputStream(sin);

            String string;
            ArrayList arrayList;
            while (true) {
                string = dataIn.readUTF();

                if (string.equals("add")) {
                    arrayList = (ArrayList) objIn.readObject();
                    objOut.writeObject(controller.runAddAction(arrayList));
                    objOut.flush();
                    continue;
                }

                if (string.equals("search")) {
                    arrayList = (ArrayList) objIn.readObject();
                    int num = dataIn.readInt();
                    objOut.writeObject(controller.runSearchAction(arrayList, num));
                    objOut.flush();
                    continue;
                }

                if (string.equals("erase")) {
                    arrayList = (ArrayList) objIn.readObject();
                    int num = dataIn.readInt();
                    objOut.writeObject(controller.runEraseAction(arrayList, num));
                    objOut.flush();
                    continue;
                }

                if (string.equals("clearSearchDialog")) {
                    controller.clearSearchList();
                    continue;
                }

                if (string.equals("frame")) {
                    objOut.writeInt(controller.getTotalRecords("frame"));
                    objOut.flush();
                    continue;
                }

                if (string.equals("searchDialog")) {
                    objOut.writeInt(controller.getTotalRecords("searchDialog"));
                    objOut.flush();
                    continue;
                }

                if (string.equals("pagepanel")) {
                    int firstIndex = dataIn.readInt();
                    int lastIndex = dataIn.readInt();
                    String parentName = dataIn.readUTF();
                    objOut.writeObject(controller.getListOfData(firstIndex, lastIndex, parentName));
                    objOut.flush();
                    continue;
                }

                if (string.equals("save")) {
                    String fileName = dataIn.readUTF();
                    objOut.writeUTF(controller.inRequest(fileName));
                    objOut.flush();
                    continue;
                }

                if (string.equals("open")) {
                    String fileName = dataIn.readUTF();
                    objOut.writeObject(controller.outRequest(fileName));
                    objOut.flush();
                    continue;
                }
            }

        } catch (IOException e) {
            closeSocket();
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public void closeSocket() {
        try {
            objIn.close();
            dataIn.close();
            objOut.close();

            sin.close();
            sout.close();

            socket.close();

            thread.interrupt();

            textArea.append("client disconnected\n");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
