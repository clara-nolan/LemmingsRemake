package org.cis.lemmings;

import java.awt.image.BufferedImage;
import java.util.List;

public class BomberLemming extends BaseLemming {
    private final AnimationCycle animationCycle;
    private int countDown;

    public BomberLemming(BufferedImage img, double xPos, double yPos) {
        super(xPos, yPos, 64, 20);
        this.animationCycle = new AnimationCycle(img, 0, 12 + 32 * 4, 64, 20, 20);
        countDown = 20;
    }

    @Override
    public void draw(DrawContext context) {
        if (countDown > 0) {
            if (countDown <= 4) {
                context.getLevel().drawBackgroundExplosion(context, this, countDown);
            }
            animationCycle.draw(context, getPx(), getPy());
            animationCycle.advanceFrame();
        }
    }

    @Override
    public void tick(Level level, List<BaseLemming> lemmings) {
        countDown--;
        if (countDown == 0) {
            die();
        }
    }

    private AnimationCycle getAnimationCycle() {
        return animationCycle;
    }

    public int getRGB(int x, int y) {
        return getAnimationCycle().getRGB(x, y);
    }
}
