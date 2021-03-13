package com.gmail.quabidlord.netfaces.core;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.gmail.quabidlord.objectserializer.Deserializer;
import com.gmail.quabidlord.objectserializer.MyConstants;
import com.gmail.quabidlord.objectserializer.Serializer;
import com.gmail.quabidlord.pathmanager.core.PathValidator;

public class MyDialog extends JDialog implements ActionListener, PropertyChangeListener {
    private JOptionPane optionPane;
    private JTextField textField;
    private final String btnString1 = "Enter";
    private final String btnString2 = "Cancel";

    private final Serializer serializer = new Serializer();
    private final Deserializer deserializer = new Deserializer();
    private final PrintStream printer = new PrintStream(System.out);
    private final MyConstants constants = new MyConstants();
    private final PathValidator pv = new PathValidator();
    private final String coordinates = constants.USRDIR + "netfaces-coordinates";

    public MyDialog(MyFrame aFrame, String title) {
        super(aFrame, true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveLocation();
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
                progExit();
            }

            @Override
            public void windowOpened(WindowEvent we) {
                restoreLocation();
            }
        });

        // Create an array of the text and components to be displayed.
        String msgString1 = "Statement or a question here";
        String msgString2 = "Another statement or question";
        textField = new JTextField(10);

        Object[] array = { msgString1, msgString2, textField };

        // Create an array specifying the number of dialog buttons
        // and their text.
        Object[] options = { btnString1, btnString2 };

        // Create the JOptionPane.
        optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, img, options,
                options[0]);

        // Make this dialog display it.
        setContentPane(optionPane);
        pack();
        setTitle(title);
        // Handle window closing correctly.
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(660, 460));
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

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
        java.net.URL imgURL = MyDialog.class.getResource(path);
        if (imgURL != null) {
            return new javax.swing.ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file at: \n" + path);
            return null;
        }
    }

    private final static javax.swing.ImageIcon img = createImageIcon("/netface-96.png");

    private final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

}
