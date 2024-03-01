package org.cis.lemmings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * GameCourt
 * <p>
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */

public class GameCourt extends JPanel {
    // Game constants
    public static final int COURT_WIDTH = 1500; // 1500
    public static final int COURT_HEIGHT = 750; // 750
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    final private BufferedImage animationSheet;
    private final JLabel status; // Current status text, i.e. "Running..."
    // the state of the game logic
    java.util.List<BaseLemming> lemmings = new ArrayList<BaseLemming>();
    private Level level;
    private LemmingType selectedLemmingType;
    private int count = 0;
    private int exitedLemmings;
    private int lemmingsSpawned;
    private int selectedLevel;
    private boolean playing = false; // whether the game is running

    public GameCourt(JLabel status) {
        selectedLevel = 1;
        animationSheet = loadImage("files/sprites.png");

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start();

        setFocusable(true);

        addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(lemmingClicked(e));
                handleLemmingClick(e);
            }
        });

        this.status = status;
        reset();
    }

    public static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));

        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        return img;
    }

    public void reset() {

        try {
            level = new Level("files/level" + selectedLevel + ".txt");
            lemmingsSpawned = 0;
            exitedLemmings = 0;
            count = 0;
            lemmings.clear();
            playing = true;
            status.setText("Running...");
        } catch (IllegalArgumentException e) {
            playing = false;
            status.setText("Failed to load level");
        }

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            status.setText(
                    "Lemmings Saved: " + exitedLemmings + "/" + level.getLemmingsNeededToWin()
            );
            // Check for the game end conditions
            if (exitedLemmings >= level.getLemmingsNeededToWin()) {
                playing = false;
                status.setText("You Win!");
            } else if (lemmings.isEmpty() && lemmingsSpawned == level.getLemmingsToSpawn()) {
                playing = false;
                status.setText("Oh no! You lose.");
            }

            spawnLemmings(level.getLemmingsToSpawn());
            checkExitedLemming();
            checkDeadLemming();

            // update the display
            for (BaseLemming lemming : lemmings) {
                lemming.tick(level, lemmings);
            }
            repaint();
            count++;
        }
    }

    private void handleLemmingClick(MouseEvent e) {
        BaseLemming lemming = lemmingClicked(e);
        if (lemming != null) {
            lemmings.remove(lemming);
            BaseLemming newLemming = null;
            switch (selectedLemmingType) {
                case BLOCKER:
                    newLemming = new BlockerLemming(
                            animationSheet,
                            lemming.getPx(), lemming.getPy()
                    );
                    break;
                case BOMBER:
                    newLemming = new BomberLemming(
                            animationSheet, lemming.getPx(), lemming.getPy()
                    );
                    break;
                case WALKER:
                    newLemming = new WalkerLemming(
                            animationSheet, lemming.getPx(), lemming.getPy()
                    );
                    break;
                default:
                    System.out.print("unknown lemming");
            }
            lemmings.add(newLemming);
        }
    }

    public BaseLemming lemmingClicked(MouseEvent e) {
        for (BaseLemming lemming : lemmings) {
            double lemmingWidth = lemming.getWidth() * getSize().getWidth() / level.getWidth();
            double lemmingHeight = lemming.getHeight() * getSize().getHeight() / level.getHeight();
            double x = lemming.getPx() * getSize().getWidth() + lemmingWidth / 2.0;
            double y = lemming.getPy() * getSize().getHeight() + lemmingHeight / 2.0;

            if (Math.abs(x - e.getX()) < lemmingHeight / 2.0 &&
                    Math.abs(y - e.getY()) < lemmingHeight / 2.0) {
                return lemming;
            }
        }
        return null;
    }

    public void setSelectedLemmingType(LemmingType selectedLemmingType) {
        this.selectedLemmingType = selectedLemmingType;
    }

    private void spawnLemmings(int numLemmings) {
        if (lemmingsSpawned < numLemmings && count > lemmingsSpawned * 30) {
            BaseLemming lemming = new WalkerLemming(
                    animationSheet, level.getStartPositionX(),
                    level.getStartPositionY()
            );
            lemmings.add(lemming);
            lemmingsSpawned++;
        }
    }

    private void checkExitedLemming() {
        Iterator<BaseLemming> lemmingIterator = lemmings.iterator();
        while (lemmingIterator.hasNext()) {
            BaseLemming lemming = lemmingIterator.next();
            double lX = lemming.getPx() * level.getWidth() + lemming.getWidth() / 2.0;
            double lY = lemming.getPy() * level.getHeight() + lemming.getHeight() / 2.0;
            if (Math.abs(level.getEndPointX() - lX) < 11
                    && Math.abs(level.getEndPointY() - lY) < lemming.getHeight()) {
                lemmingIterator.remove();
                exitedLemmings++;
            }
        }
    }

    public int getExitedLemmings() {
        return exitedLemmings;
    }

    private void checkDeadLemming() {
        Iterator<BaseLemming> lemmingIterator = lemmings.iterator();
        while (lemmingIterator.hasNext()) {
            BaseLemming lemming = lemmingIterator.next();
            if (!lemming.isAlive()) {
                lemmingIterator.remove();
            }
        }
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (level != null) {
            DrawContext context = new DrawContext(g, getSize(), level);
            level.draw(context);
            for (BaseLemming lemming : lemmings) {
                lemming.draw(context);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
