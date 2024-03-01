package org.cis.lemmings;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Level {
    private final BufferedImage img;
    final private String filename;
    final private String imgFilename;
    private double startPositionX;
    private double startPositionY;
    private int width;
    private int height;
    private double endPointX;
    private double endPointY;
    private int lemmingsNeededToWin;
    private int lemmingsToSpawn;

    public Level(String filename) {
        enum CsvEntry {
            BACKGROUND_IMAGE, START_POSITION, TOTAL_WALKER_LEMMINGS,
            LEMMINGS_NEEDED_TO_WIN, ENDPOINT
        }
        try {
            this.filename = filename;
            ArrayList<String[]> csv = new CsvReader(filename).getCsvData();
            imgFilename = csv.get(CsvEntry.BACKGROUND_IMAGE.ordinal())[1];
            this.img =
                    GameCourt.loadImage(csv.get(CsvEntry.BACKGROUND_IMAGE.ordinal())[1]);
            this.startPositionX =
                    Double.parseDouble(csv.get(CsvEntry.START_POSITION.ordinal())[1]);
            this.startPositionY =
                    Double.parseDouble(csv.get(CsvEntry.START_POSITION.ordinal())[2]);
            this.lemmingsToSpawn = Integer
                    .parseInt(csv.get(CsvEntry.TOTAL_WALKER_LEMMINGS.ordinal())[1]);
            this.lemmingsNeededToWin = Integer
                    .parseInt(csv.get(CsvEntry.LEMMINGS_NEEDED_TO_WIN.ordinal())[1]);
            this.endPointX =
                    Double.parseDouble(csv.get(CsvEntry.ENDPOINT.ordinal())[1]);
            this.endPointY =
                    Double.parseDouble(csv.get(CsvEntry.ENDPOINT.ordinal())[2]);
            this.width = img.getWidth();
            this.height = img.getHeight();

        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        if (startPositionX < 0 || startPositionY < 0 || startPositionX >= width
                || startPositionY >= height) {
            throw new IllegalArgumentException("invalid input");
        }

        if (endPointX < 0 || endPointY < 0 || endPointX >= width || endPointY >= height) {
            throw new IllegalArgumentException("invalid input");
        }

        if (lemmingsNeededToWin <= 0 || lemmingsToSpawn <= 0) {
            throw new IllegalArgumentException("invalid input");
        }
    }

    public void draw(DrawContext context) {
        context.getGraphics().drawImage(
                img, 0, 0, context.getDimension().width,
                context.getDimension().height, null
        );
    }

    public double getStartPositionX() {
        return startPositionX;
    }

    public double getStartPositionY() {
        return startPositionY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getLemmingsNeededToWin() {
        return lemmingsNeededToWin;
    }

    public void setLemmingsNeededToWin(int lemmingsNeededToWin) {
        this.lemmingsNeededToWin = lemmingsNeededToWin;
    }

    public int getLemmingsToSpawn() {
        return lemmingsToSpawn;
    }

    public void setLemmingsToSpawn(int lemmingsToSpawn) {
        this.lemmingsToSpawn = lemmingsToSpawn;
    }

    public double getEndPointX() {
        return endPointX;
    }

    public void setEndPointX(double endPointX) {
        this.endPointX = endPointX;
    }

    public double getEndPointY() {
        return endPointY;
    }

    public void setEndPointY(double endPointY) {
        this.endPointY = endPointY;
    }

    public boolean saveToFile(String saveFilename) {
        try {
            File file = Paths.get(saveFilename).toFile();
            FileWriter f = new FileWriter(saveFilename, false);
            BufferedWriter buff = new BufferedWriter(f);
            buff.write("BackGroundImage," + imgFilename);
            buff.newLine();
            buff.write("StartPosition," + startPositionX + "," + startPositionY);
            buff.newLine();
            buff.write("TotalWalkerLemmings," + lemmingsToSpawn);
            buff.newLine();
            buff.write("LemmingsNeededToWin," + lemmingsNeededToWin);
            buff.newLine();
            buff.write("EndPoint," + endPointX + "," + endPointY);
            buff.newLine();
            buff.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    public boolean canMove(BaseLemming lemming, int xOffset, int yOffset) {
        int scaledX = (int) (lemming.getPx() * img.getWidth());
        int scaledY = (int) (lemming.getPy() * img.getHeight());
        boolean hit = false;
        int y = lemming.getHeight() - 1;
        for (int x = 26; x < lemming.getWidth() - 26; x++) {
            int imgX = x + scaledX + xOffset;
            int imgY = y + scaledY + yOffset;
            if (imgX >= 0 && imgY >= 0 && imgX < img.getWidth() && imgY < img.getHeight()) {
                int backgroundColor = img.getRGB(
                        x + scaledX + xOffset,
                        y + scaledY + yOffset
                );
                // Format is ARGB so blue is least significant byte.
                int bgb = backgroundColor & 0xFF;
                int bgg = (backgroundColor >> 8) & 0xFF;
                int bgr = (backgroundColor >> 16) & 0xFF;
                boolean isBackground = bgr == 0 && bgg == 0 && bgb == 0;
                boolean isExit = bgr == 185 && bgg == 122 && bgb == 87;
                if (!(isBackground || isExit)) {
                    hit = true;
                    break;
                }
            }
        }

        return !hit;
    }

    public void drawBackgroundExplosion(
            DrawContext context, BaseLemming lemming,
            int countDown
    ) {
        int scaledX = (int) (lemming.getPx() * img.getWidth() + 22);
        int scaledY = (int) (lemming.getPy() * img.getHeight());
        // Only draw explosion for final four frames
        int size = (4 - countDown) * 10;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int imgX = x + scaledX;
                int imgY = y + scaledY;
                if (imgX >= 0 && imgY >= 0 && imgX < img.getWidth() && imgY < img.getHeight()) {
                    // The image is in ARGB format, so OXFF000000 is opaque black
                    img.setRGB(x + scaledX, y + scaledY, 0XFF000000);
                }
            }
        }
    }
}
