package com.gmail.quabidlord.netfaces.core;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class NetfaceFinder {
    private final ArrayList<String> netfaces = new ArrayList<String>();
    private final PrintStream printer = new PrintStream(System.out);
    private boolean hasNetfaces = false;

    public NetfaceFinder() {
        super();
        findNetfaces();
    }

    private final void findNetfaces() {
        try {
            Enumeration<NetworkInterface> nf = NetworkInterface.getNetworkInterfaces();
            while (nf.hasMoreElements()) {
                NetworkInterface ni = nf.nextElement();

                Enumeration<InetAddress> e2 = ni.getInetAddresses();

                while (e2.hasMoreElements()) {
                    InetAddress ip = e2.nextElement();
                    netfaces.add(ni.getDisplayName() + " " + ip.toString());
                    println(ni.getDisplayName() + " " + ip.toString());
                }
            }
            hasNetfaces = netfaces.size() > 0;
        } catch (SocketException se) {
            println(se.getMessage());
            return;
        }
    }

    public ArrayList<String> getNetFaces() {
        if (hasNetfaces) {
            return netfaces;
        }
        return null;
    }

    public final void resetFaces() {
        netfaces.clear();
        findNetfaces();
    }

    public final int size() {
        return netfaces.size();
    }

    private final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }
}
