package com.gmail.quabidlord.netfaces.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.gmail.quabidlord.netfaces.core.abstracts.CustomDialog;
import com.gmail.quabidlord.objectserializer.Deserializer;
import com.gmail.quabidlord.objectserializer.MyConstants;
import com.gmail.quabidlord.objectserializer.Serializer;
import com.gmail.quabidlord.pathmanager.core.PathValidator;

public class MyDialog extends CustomDialog implements ActionListener, PropertyChangeListener {
    /**
     *
     */
    private static final long serialVersionUID = 6962369801192558431L;
    private JOptionPane optionPane;
    private int optionPaneChoice;
    private JComboBox<Object> networkInterfaceComboBox;
    private String selectedItem = "";

    private final String btnEnter = "Enter";
    private final String btnCancel = "Cancel";
    private final String btnAck = "Acknowledged";
    private final String btnDef = "Done";

    private final Serializer serializer = new Serializer();
    private final Deserializer deserializer = new Deserializer();
    private final PrintStream printer = new PrintStream(System.out);
    private final MyConstants constants = new MyConstants();
    private final PathValidator pv = new PathValidator();
    private final String coordinates = constants.USRDIR + "netfaces-coordinates";
    private final javax.swing.ImageIcon img = createImageIcon("/netface-48.png");

    NetfaceFinder nf = new NetfaceFinder();
    NetfaceInterrogator nfi = new NetfaceInterrogator();

    public MyDialog(JFrame frame) {
        super(frame);
        createGui();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Pattern joptionpanePattern = Pattern.compile("^(javax.swing.JOptionPane)");
        String source = String.valueOf(evt.getSource());
        JOptionPane jop = null;

        if (joptionpanePattern.matcher(source).find()) {
            jop = (JOptionPane) evt.getSource();
            String value = String.valueOf(jop.getValue()).trim().toLowerCase();

            switch (value) {
            case "enter":
                nfi.interrogate(selectedItem);
                break;

            case "cancel":

                break;

            default:

                break;
            }
        }

        print("\n");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JComboBox<Object> jcb = (JComboBox) ae.getSource();
        String si = String.valueOf(jcb.getItemAt(networkInterfaceComboBox.getSelectedIndex()));
        selectedItem = si;
        println("\n\tSelected Item: " + selectedItem + "\n");
    }

    protected final void createGui() {
        final String[] networkInterfaces = new String[nf.size()];
        String status = "";
        String title = "Network Interface Cards";
        String msgString2 = "Adapter Name /IP";
        String msgString1 = status;
        int i = 0;

        switch (nf.size()) {
        case 1:
            status = nf.size() + " Adapter Address";
            break;

        default:
            status = nf.size() + " Adapter Addresses";
            break;
        }

        for (String face : nf.getNetFaces()) {
            networkInterfaces[i] = face;
            i++;
        }

        networkInterfaceComboBox = new JComboBox<Object>(networkInterfaces);
        networkInterfaceComboBox.setActionCommand("netList_combo");
        networkInterfaceComboBox.setName("netList_combobox");
        networkInterfaceComboBox.setSelectedIndex(0);
        networkInterfaceComboBox.addActionListener(this);
        selectedItem = String.valueOf(networkInterfaceComboBox.getItemAt(networkInterfaceComboBox.getSelectedIndex()));

        Object[] optionPaneConfig = { msgString1, msgString2, networkInterfaceComboBox };

        // Create an array specifying the number of dialog buttons
        // and their text.
        Object[] options = { btnDef };
        optionPane = new JOptionPane();
        optionPane.setName("TheOptionPane");
        optionPane.addPropertyChangeListener(this);

        optionPaneChoice = optionPane.showInternalOptionDialog(null, networkInterfaceComboBox, title,
                JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, img, options, options[0]);

        print("\n\tChosen Option: " + optionPaneChoice + "\n\n");
    }

    /** Returns ImageIcon */
    protected javax.swing.ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MyDialog.class.getResource(path);
        if (imgURL != null) {
            return new javax.swing.ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file at: \n" + path);
            return null;
        }
    }

}
