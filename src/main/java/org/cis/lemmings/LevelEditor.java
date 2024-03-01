package org.cis.lemmings;

import javax.swing.*;
import java.awt.*;

public class LevelEditor extends JPanel {
    public LevelEditor(String filename) {
        JTextField editSpawnLemmings = new JTextField(16);
        JTextField editLemmingsToWin = new JTextField(16);
        JLabel label1 = new JLabel("Edit Amount of Lemmings to Spawn");
        JLabel label2 = new JLabel("Edit Amount of Lemmings to Win");
        label1.setLabelFor(editSpawnLemmings);
        label2.setLabelFor(editLemmingsToWin);
        this.add(label2);
        this.add(editLemmingsToWin);
        this.add(label1);
        this.add(editSpawnLemmings);
        final JButton save = new JButton("Save");
        this.add(save);

        try {
            Level level = new Level(filename);

            editSpawnLemmings.setText("" + level.getLemmingsToSpawn());
            editLemmingsToWin.setText("" + level.getLemmingsNeededToWin());

            save.addActionListener(e -> {
                level.setLemmingsToSpawn(Integer.parseInt(editSpawnLemmings.getText()));
                level.setLemmingsNeededToWin(Integer.parseInt(editLemmingsToWin.getText()));

                try {
                    level.saveToFile(filename);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }
            });
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}
