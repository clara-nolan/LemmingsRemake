package org.cis.lemmings;

import java.awt.image.BufferedImage;
import java.util.List;

public class BlockerLemming extends BaseLemming {

    private final AnimationCycle animationCycle;

    public BlockerLemming(BufferedImage img, double xPos, double yPos) {
        super(xPos, yPos, 64, 20);
        this.animationCycle = new AnimationCycle(img, 0, 12 + 32 * 6, 64, 20, 6);
    }

    @Override
    public void draw(DrawContext context) {
        animationCycle.draw(context, getPx(), getPy());
        animationCycle.advanceFrame();
    }

    @Override
    public void tick(Level level, List<BaseLemming> lemmings) {

    }

    // Checks if current lemming is blocked by a blocker lemming
    public static boolean isBlocked(
            BaseLemming curr, List<BaseLemming> lemmings, Level level, int xOffset
    ) {
        double currX = curr.getPx() * level.getWidth() + curr.getWidth() / 2.0 + xOffset;
        double currY = curr.getPy() * level.getHeight() + curr.getHeight() / 2.0;
        for (BaseLemming lemming : lemmings) {
            if (lemming instanceof BlockerLemming) {
                double lX = lemming.getPx() * level.getWidth() + lemming.getWidth() / 2.0;
                double lY = lemming.getPy() * level.getHeight() + lemming.getHeight() / 2.0;
                if (Math.abs(currX - lX) < 11 && Math.abs(currY - lY) < lemming.getHeight()) {
                    return true;
                }
            }
        }
        return false;
    }

    private AnimationCycle getAnimationCycle() {
        return animationCycle;
    }

    public int getRGB(int x, int y) {
        return getAnimationCycle().getRGB(x, y);
    }
}
