package com.gmail.quabidlord.netfaces.core;

import java.awt.Point;
import java.io.Serializable;

public class WindowCoordinates implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int x = 0;
    public int y = 0;

    public WindowCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getLocation() {
        return new Point(x, y);
    }
}
