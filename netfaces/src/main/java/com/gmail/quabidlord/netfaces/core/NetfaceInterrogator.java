package com.gmail.quabidlord.netfaces.core;

import java.io.PrintStream;

public class NetfaceInterrogator {
    private final PrintStream printer = new PrintStream(System.out);
    private String inetaddress;
    private String name;

    public NetfaceInterrogator() {
        super();
    }

    public final void interrogate(String netfaceInfo) {
        if (null != netfaceInfo && !netfaceInfo.isEmpty() && !netfaceInfo.isBlank()) {
            String[] infoSplit = netfaceInfo.split(" ");
            name = infoSplit[0];
            inetaddress = infoSplit[1].replace("/", "");
            println("Netface Name: " + name);
            println("Netface Address: " + inetaddress);
            print("\n\tNetwork Interface: " + netfaceInfo + "\n");
        }
    }

    private final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    private final void print(Object obj) {
        printer.print(String.valueOf(obj));
    }

}
