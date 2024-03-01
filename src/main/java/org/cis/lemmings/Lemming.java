package org.cis.lemmings;

import java.util.List;

interface Lemming {

    // **********************************************************************************
    // * GETTERS
    // **********************************************************************************
    public abstract double getPx();

    public abstract double getPy();

    public abstract int getWidth();

    public abstract int getHeight();

    // **************************************************************************
    // * SETTERS
    // **************************************************************************
    public abstract void setPx(double px);

    public abstract void setPy(double py);

    public abstract void die();

    public abstract boolean isAlive();
    // **************************************************************************
    // * UPDATES AND OTHER METHODS
    // **************************************************************************

    public abstract void draw(DrawContext context);

    public abstract void tick(Level level, List<BaseLemming> lemmings);

    public abstract int getRGB(int x, int y);
}
