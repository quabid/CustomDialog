package com.gmail.quabidlord.netfaces;

import javax.swing.SwingUtilities;

import com.gmail.quabidlord.netfaces.core.MyFrame;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        final MyFrame frame = new MyFrame();
                    }
                });
            }
        });
    }
}
