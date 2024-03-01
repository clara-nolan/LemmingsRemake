package org.cis.lemmings;

import java.awt.image.BufferedImage;

public class AnimationCycle {
    private final BufferedImage img;
    private final int xOffset;
    private final int yOffset;
    private final int frameWidth;
    private final int frameHeight;
    private final int numFrames;
    private int currFrame;

    public AnimationCycle(
            BufferedImage img, int xOffset, int yOffset,
            int frameWidth, int frameHeight, int numFrames
    ) {
        this.img = img;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.numFrames = numFrames;
        this.currFrame = 0;

    }

    public void draw(DrawContext context, double x, double y) {
        context.getGraphics().drawImage(
                img, context.scaleX(x), context.scaleY(y),
                context.scaleX(x) +
                        (int) ((frameWidth * context.getDimension().getWidth())
                                / context.getLevel().getWidth()),
                context.scaleY(y) + (int) ((frameHeight * context.getDimension().getHeight())
                        / context.getLevel().getHeight()),
                xOffset + currFrame * frameWidth, yOffset,
                frameWidth + xOffset + currFrame * frameWidth,
                yOffset + frameHeight, null
        );
    }

    public int getCurrFrame() {
        return currFrame;
    }

    public void advanceFrame() {
        currFrame = (currFrame + 1) % numFrames;
    }

    public int getRGB(int x, int y) {
        x = Math.min(Math.max(x, 0), frameWidth - 1);
        x += xOffset + currFrame * frameWidth;
        y = Math.min(Math.max(y, 0), frameHeight - 1);
        y += yOffset;
        return img.getRGB(x, y);
    }
}
