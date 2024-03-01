package org.cis;

import org.cis.lemmings.RunLemmings;

import javax.swing.*;

public class Game {
    /**
        Run the game here
     */
    public static void main(String[] args) {
        // Set the game you want to run here
        Runnable game = new RunLemmings();

        SwingUtilities.invokeLater(game);
    }
}
