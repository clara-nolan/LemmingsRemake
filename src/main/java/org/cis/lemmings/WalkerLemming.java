package org.cis.lemmings;

import java.awt.image.BufferedImage;
import java.util.List;

public class WalkerLemming extends BaseLemming {
    private final AnimationCycle leftAnimationCycle;
    private final AnimationCycle rightAnimationCycle;
    private final AnimationCycle splatAnimationCycle;
    private int fallDistance;
    private Direction direction;

    public WalkerLemming(BufferedImage img, double xPos, double yPos) {
        super(xPos, yPos, 64, 20);
        fallDistance = 0;
        this.rightAnimationCycle = new AnimationCycle(img, 0, 12, 64, 20, 8);
        this.leftAnimationCycle = new AnimationCycle(img, 512, 12, 64, 20, 8);
        this.splatAnimationCycle = new AnimationCycle(img, 768, 12 + 32 * 4, 64, 20, 8);
        direction = Direction.RIGHT;
    }

    @Override
    public void draw(DrawContext context) {
        if (direction == Direction.SPLAT) {
            splatAnimationCycle.draw(context, getPx(), getPy());
            splatAnimationCycle.advanceFrame();
            if (splatAnimationCycle.getCurrFrame() == 7) {
                die();
            }
        } else if (direction == Direction.LEFT) {
            leftAnimationCycle.draw(context, getPx(), getPy());
            leftAnimationCycle.advanceFrame();
        } else {
            rightAnimationCycle.draw(context, getPx(), getPy());
            rightAnimationCycle.advanceFrame();
        }
    }

    @Override
    public void tick(Level level, List<BaseLemming> lemmings) {
        setPy(((int) (getPy() * level.getHeight())) / level.getHeight());
        setPx(((int) (getPx() * level.getWidth())) / level.getWidth());

        // Test if lemming can move down
        if (level.canMove(this, 0, 1)) {
            setPy(getPy() + 1.0 / level.getHeight());
            // Count how far the lemming has fallen
            fallDistance++;

        } else {
            if (fallDistance > 106) {
                // If this block is reached, fallen too far and hit something
                direction = Direction.SPLAT;
            } else {
                fallDistance = 0;
                if (direction == Direction.LEFT) {
                    // Test if lemming can keep moving left
                    if (level.canMove(this, -1, 0)
                            && !BlockerLemming.isBlocked(this, lemmings, level, -1)) {
                        setPx(getPx() - 1.0 / level.getWidth());
                        // Test if lemming can climb left and up
                    } else if (level.canMove(this, -1, -1)
                            && !BlockerLemming.isBlocked(this, lemmings, level, -1)) {
                        setPx(getPx() - 1.0 / level.getWidth());
                        setPy(getPy() - 1.0 / level.getHeight());
                    } else {
                        direction = Direction.RIGHT;
                    }
                } else {
                    // Test if lemming can keep moving right
                    if (level.canMove(this, 1, 0)
                            && !BlockerLemming.isBlocked(this, lemmings, level, 1)) {
                        setPx(getPx() + 1.0 / level.getWidth());
                        // Test if lemming can climb right and up
                    } else if (level.canMove(this, 1, -1)
                            && !BlockerLemming.isBlocked(this, lemmings, level, 1)) {
                        setPx(getPx() + 1.0 / level.getWidth());
                        setPy(getPy() - 1.0 / level.getHeight());
                    } else {
                        direction = Direction.LEFT;
                    }
                }
            }
        }
    }

    private AnimationCycle getAnimationCycle() {
        if (direction == Direction.LEFT) {
            return leftAnimationCycle;
        } else {
            return rightAnimationCycle;
        }
    }

    public int getRGB(int x, int y) {
        return getAnimationCycle().getRGB(x, y);
    }
}
