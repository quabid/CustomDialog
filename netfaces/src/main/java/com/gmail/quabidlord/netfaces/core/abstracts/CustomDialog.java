package com.gmail.quabidlord.netfaces.core.abstracts;

import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

public abstract class CustomDialog extends JDialog {
    private final PrintStream printer = new PrintStream(System.out);
    private final ImageIcon img = createImageIcon("/netface-48.png");

    public CustomDialog() {
        super();
    }

    public CustomDialog(JFrame frame) {
        super(frame);
    }

    /** Returns ImageIcon */
    protected javax.swing.ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CustomDialog.class.getResource(path);
        if (imgURL != null) {
            return new javax.swing.ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file at: \n" + path);
            return null;
        }
    }

    protected void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    protected void print(Object obj) {
        printer.print(String.valueOf(obj));
    }

    public String toString() {
        return "Custom JDialog";
    }
}
