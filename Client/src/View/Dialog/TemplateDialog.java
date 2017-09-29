package View.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemplateDialog {
    private JDialog dialog;

    private JRadioButton []radioButt = new JRadioButton[3];
    private JTextField []textField = new JTextField[2];

    private JButton actionButt = new JButton();
    private JButton cleanButt = new JButton("Clean");

    public TemplateDialog(JDialog dialog) {
        this.dialog = dialog;

        for (int i = 0; i < 3; i++) {
            if (i < 2) {
                textField[i] = new JTextField();
            }

            radioButt[i] = new JRadioButton();
        }
    }

    public void setDialog() {
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dialog.getContentPane().setBackground(Color.gray);
        dialog.setLayout(new GridBagLayout());
        dialog.setResizable(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        radioButt[0].setSelected(true);

        JLabel []labelField = new JLabel[2];
        for (int i = 0; i < 2; i++) {
            labelField[i] = new JLabel();
        }

        labelField[0].setText("Номер дома");
        labelField[1].setText("Фамилия");

        radioButt[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelField[0].setText("Номер дома");
                labelField[1].setText("Фамилия");
            }
        });

        radioButt[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelField[0].setText("Улица");
                labelField[1].setText("Номер квартиры");
            }
        });

        radioButt[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelField[0].setText("Фамилия");
                labelField[1].setText("Цифры номера дома");
            }
        });

        actionButt.setBackground(Color.yellow);
        cleanButt.setBackground(Color.yellow);

        JLabel []labelButt = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            labelButt[i] = new JLabel();
        }

        labelButt[0].setText("по номеру дома и фамилии");
        labelButt[1].setText("по улице и квартире");
        labelButt[2].setText("<html>по фамилии и цифрам <br> встречающимся в номере дома</hrml>");

        GridBagConstraints c = new GridBagConstraints();

        c.gridy = -1;
        c.insets = new Insets(10, 10, 10, 10);

        for (int i = 0; i < 3; i++) {
            if (i < 2) {
                labelField[i].setForeground(Color.yellow);
                textField[i].setColumns(15);
            }

            labelButt[i].setForeground(Color.yellow);
            radioButt[i].setBackground(Color.gray);
            buttonGroup.add(radioButt[i]);

            c.gridy++;
            c.gridx = 0;
            dialog.add(radioButt[i], c);
            c.gridx++;
            dialog.add(labelButt[i], c);
        }

        for (int i = 0; i < 2; i++) {
            c.gridy++;
            c.gridx = 0;
            dialog.add(textField[i], c);
            c.gridx++;
            dialog.add(labelField[i], c);
        }

        c.gridx = 0;
        c.gridy++;
        dialog.add(actionButt, c);
        c.gridx++;
        dialog.add(cleanButt, c);

        dialog.pack();

        WindowFocus windowFocus = new WindowFocus(dialog);
        dialog.addWindowFocusListener(windowFocus);
    }

    public JButton getActionButt() {
        return actionButt;
    }

    public JRadioButton[] getRadioButt() {
        return radioButt;
    }

    public JTextField[] getTextField() {
        return textField;
    }

    public JButton getCleanButt() {
        return cleanButt;
    }
}
