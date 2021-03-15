package com.gmail.quabidlord.netfaces.core.abstracts;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.gmail.quabidlord.netfaces.core.WindowCoordinates;
import com.gmail.quabidlord.objectserializer.Deserializer;
import com.gmail.quabidlord.objectserializer.MyConstants;
import com.gmail.quabidlord.objectserializer.Serializer;
import com.gmail.quabidlord.pathmanager.core.PathValidator;

public abstract class CustomDialog extends JDialog {
    private final Serializer serializer = new Serializer();
    private final Deserializer deserializer = new Deserializer();
    private final PrintStream printer = new PrintStream(System.out);
    private final MyConstants constants = new MyConstants();
    private final PathValidator pv = new PathValidator();
    private final String coordinates = constants.USRDIR + "netfaces-coordinates";
    private String[] options;

    public CustomDialog() {
        super();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveLocation();
                progExit();
            }

            @Override
            public void windowOpened(WindowEvent we) {
                restoreLocation();
            }
        });

    }

    public CustomDialog(JFrame frame) {
        super(frame);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveLocation();
                progExit();
            }

            @Override
            public void windowOpened(WindowEvent we) {
                restoreLocation();
            }
        });

    }

    public CustomDialog(JFrame frame, String title) {
        super(frame, title);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveLocation();
                progExit();
            }

            @Override
            public void windowOpened(WindowEvent we) {
                restoreLocation();
            }
        });

    }

    public CustomDialog(JFrame frame, String title, boolean modal) {
        super(frame, title, modal);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveLocation();
                progExit();
            }

            @Override
            public void windowOpened(WindowEvent we) {
                restoreLocation();
            }
        });

    }

    private final void createGui(JFrame frame) {
        int n = JOptionPane.showOptionDialog(frame, "Would you like green eggs and ham?", "A Silly Question",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
                options, // the titles of buttons
                options[0]); // default button title
    }

    private final void saveLocation() {
        println("\n\tSaving " + coordinates + "\n");
        final WindowCoordinates wc = new WindowCoordinates(getX(), getY());
        serializer.serialize(wc, coordinates);
    }

    private final void restoreLocation() {
        if (pv.pathExists(coordinates)) {
            println("\n\tFound " + coordinates + "\n");
            final WindowCoordinates wc = (WindowCoordinates) deserializer.deserialize(coordinates);
            setLocation(wc.getLocation());
        }
    }

    private final void progExit() {
        super.dispose();
        getOwner().dispose();
    }

    /** Returns ImageIcon */
    private static javax.swing.ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CustomDialog.class.getResource(path);
        if (imgURL != null) {
            return new javax.swing.ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file at: \n" + path);
            return null;
        }
    }

    private final static javax.swing.ImageIcon img = createImageIcon("/netface-48.png");

    private final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    private final void print(Object obj) {
        printer.print(String.valueOf(obj));
    }

}
