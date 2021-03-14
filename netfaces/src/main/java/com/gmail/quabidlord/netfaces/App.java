package com.gmail.quabidlord.netfaces;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.gmail.quabidlord.netfaces.core.MyDialog;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame();
                final MyDialog dialog = new MyDialog(frame, "Network Interfaces");
            }
        });
    }
}
