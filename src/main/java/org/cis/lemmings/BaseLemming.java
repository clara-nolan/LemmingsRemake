package org.cis.lemmings;

import java.util.List;

public abstract class BaseLemming implements Lemming {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private double px;
    private double py;

    /* Size of object, in pixels. */
    private final int width;
    private final int height;

    private boolean isAlive;

    /**
     * Constructor
     */
    public BaseLemming(double px, double py, int width, int height) {
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;
        this.isAlive = true;

        // take the width and height into account when setting the bounds for
        // the upper left corner of the object.

    }

    // **********************************************************************************
    // * GETTERS
    // **********************************************************************************
    public double getPx() {
        return this.px;
    }

    public double getPy() {
        return this.py;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    // **************************************************************************
    // * SETTERS
    // **************************************************************************
    public void setPx(double px) {
        this.px = px;

    }

    public void setPy(double py) {
        this.py = py;

    }

    public void die() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;

    }
    // **************************************************************************
    // * UPDATES AND OTHER METHODS
    // **************************************************************************

    public abstract void draw(DrawContext context);

    public abstract void tick(Level level, List<BaseLemming> lemmings);

    public abstract int getRGB(int x, int y);
}
