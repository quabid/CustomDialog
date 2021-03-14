package com.gmail.quabidlord.netfaces.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
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

    NetfaceFinder nf = new NetfaceFinder();

    public MyDialog(JFrame aFrame, String title) {
        super(aFrame, true);
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

        String[] networkInterfaces = new String[nf.size()];
        String status = "";

        switch (nf.size()) {
        case 1:
            status = nf.size() + " Adapter Address";
            break;

        default:
            status = nf.size() + " Adapter Addresses";
            break;
        }

        String msgString2 = "Adapter Name /IP";
        String msgString1 = status;
        int i = 0;

        for (String face : nf.getNetFaces()) {
            networkInterfaces[i] = face;
            i++;
        }

        JComboBox netList = new JComboBox(networkInterfaces);
        netList.setActionCommand("netList_combo");
        netList.addPropertyChangeListener(this);
        netList.setSelectedIndex(0);
        netList.setName("netList_combobox");
        netList.addActionListener(this);

        Object[] array = { msgString1, msgString2, netList };

        // Create an array specifying the number of dialog buttons
        // and their text.
        Object[] options = { btnString1, btnString2 };

        // Create the JOptionPane.
        optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, img, options,
                options[0]);
        optionPane.setName("Option Pane");
        optionPane.addPropertyChangeListener(this);

        // Make this dialog display it.
        setContentPane(optionPane);
        pack();
        setTitle(title);
        // Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        println("Old Value: " + evt.getOldValue() + " New Value: " + evt.getNewValue() + "\n\tSource Name: "
                + "\n\tPropery Name: " + propertyName + "\n");

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JComboBox jcb = (JComboBox) ae.getSource();
        println("\n\tAction performed: " + ae.getActionCommand() + "\n\t" + jcb.getName());
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

    private final static javax.swing.ImageIcon img = createImageIcon("/netface-48.png");

    private final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

}
