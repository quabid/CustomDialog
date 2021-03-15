package com.gmail.quabidlord.netfaces.core;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import com.gmail.quabidlord.objectserializer.Deserializer;
import com.gmail.quabidlord.objectserializer.MyConstants;
import com.gmail.quabidlord.objectserializer.PathValidator;
import com.gmail.quabidlord.objectserializer.Serializer;

public class MyFrame extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 4673401792562825489L;
    private final Menu menu = new Menu();
    private final Serializer serializer = new Serializer();
    private final Deserializer deserializer = new Deserializer();
    private final PrintStream printer = new PrintStream(System.out);
    private final MyConstants constants = new MyConstants();
    private final PathValidator pv = new PathValidator();
    private final String coordinates = constants.USRDIR + "netface-coordinates";

    /** This variable contains the path to the window's icon image. */
    private final static java.net.URL path = MyFrame.class.getResource("/netface-96.png");

    /** @see java.awt.Toolkit */
    protected final static Image imageIcon = Toolkit.getDefaultToolkit().createImage(path);

    public MyFrame() {
        super();
        setJMenuBar(menu.buildMenu());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveLocation();
            }

            @Override
            public void windowOpened(WindowEvent we) {
                restoreLocation();
            }
        });
        setIconImage(imageIcon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(660, 460));
        pack();
        setVisible(true);
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

    private final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    private class Menu {
        // Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        private final JMenuBar buildMenu() {
            // Create the menu bar.
            menuBar = new JMenuBar();

            // Build the first menu.
            menu = new JMenu("A Menu");
            menu.setMnemonic(KeyEvent.VK_A);
            menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
            menuBar.add(menu);

            return menuBar;
        }
    }
}
