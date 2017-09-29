package View.Dialog;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class WindowFocus implements WindowFocusListener {
    private JDialog dialog;

    public WindowFocus(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        dialog.setVisible(true);
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        dialog.setVisible(false);
    }
}