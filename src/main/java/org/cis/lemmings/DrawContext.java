package org.cis.lemmings;

import java.awt.*;

public class DrawContext {
    private Graphics graphics;
    private Dimension dimension;
    private Level level;

    public DrawContext(Graphics graphics, Dimension dimension, Level level) {
        this.graphics = graphics;
        this.dimension = dimension;
        this.level = level;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int scaleX(double x) {
        return (int) (x * dimension.getWidth());
    }

    public int scaleY(double y) {
        return (int) (y * dimension.getHeight());
    }

    public Level getLevel() {
        return level;
    }
}
