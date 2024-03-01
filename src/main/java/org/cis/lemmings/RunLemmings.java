package org.cis.lemmings;

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunLemmings implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Lemmings");
        frame.setLocation(300, 300);

        // Create menu frame
        final JFrame menuFrame = new JFrame("Menu");
        menuFrame.setSize(500, 300);
        menuFrame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.NORTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);

        // Lemming type buttons
        final JRadioButton blockerButton = new JRadioButton("Blocker");
        blockerButton.addActionListener(e -> court.setSelectedLemmingType(LemmingType.BLOCKER));
        final JRadioButton bomberButton = new JRadioButton("Bomber");
        bomberButton.addActionListener(e -> court.setSelectedLemmingType(LemmingType.BOMBER));
        ButtonGroup group = new ButtonGroup();
        group.add(blockerButton);
        group.add(bomberButton);
        control_panel.add(blockerButton);
        control_panel.add(bomberButton);

        // Create edit frame

        final JPanel levelSelect = new JPanel();
        menuFrame.add(levelSelect, BorderLayout.CENTER);
        final JRadioButton levelOneButton = new JRadioButton("Level 1");
        levelOneButton.addActionListener(e -> court.setSelectedLevel(1));
        levelOneButton.setSelected(true);
        final JRadioButton levelTwoButton = new JRadioButton("Level 2");
        levelTwoButton.addActionListener(e -> court.setSelectedLevel(2));
        final JRadioButton levelThreeButton = new JRadioButton("Level 3");
        levelThreeButton.addActionListener(e -> court.setSelectedLevel(3));
        ButtonGroup levels = new ButtonGroup();
        levels.add(levelOneButton);
        levels.add(levelTwoButton);
        levels.add(levelThreeButton);
        levelSelect.add(levelOneButton);
        levelSelect.add(levelTwoButton);
        levelSelect.add(levelThreeButton);

        final JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> {
            menuFrame.setVisible(false);
            frame.setVisible(true);
            court.reset();
        });
        levelSelect.add(startGameButton, BorderLayout.EAST);

        final JButton editButton = new JButton("Edit Level");
        editButton.addActionListener(e -> {
            LevelEditor levelEditor = new LevelEditor(
                    "files/level" + court.getSelectedLevel() + ".txt"
            );
            final JDialog editDialog = new JDialog(menuFrame, "Edit");
            editDialog.add(levelEditor);
            editDialog.setSize(300, 300);
            editDialog.setVisible(true);
        });
        levelSelect.add(editButton, BorderLayout.EAST);

        final JButton exitToLevelSelect = new JButton("Exit to Level Select");
        exitToLevelSelect.addActionListener(e -> {
            menuFrame.setVisible(true);
            frame.setVisible(false);
            court.reset();
        });
        control_panel.add(exitToLevelSelect);

        JOptionPane.showMessageDialog(
                frame,
                "Instructions\n"
                        + "To play Lemmings, you must guide each lemming to the " +
                        "exit door\n" +
                        "by turning walker lemmings into blockers, which prevent " +
                        "lemmings from falling off an edge\n"
                        + "or into bombers, which explode and create a crater " +
                        "which other lemmings can fall into."
        );
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
        levelSelect.setVisible(true);

        // Start game
        court.reset();
    }
}
