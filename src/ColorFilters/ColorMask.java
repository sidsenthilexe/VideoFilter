package ColorFilters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class ColorMask implements PixelFilter, Interactive {
    private int x, y;
    private short thresholdR, thresholdG, thresholdB;
    private double maxDistance;

    public ColorMask() {
        x = 0;
        y = 0;

        String response = JOptionPane.showInputDialog(null, "Enter threshold color distance");
        maxDistance = Double.parseDouble(response);
        maxDistance = Math.min(441, (Math.max(0, maxDistance)));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        thresholdR = red[y][x];
        thresholdG = green[y][x];
        thresholdB = blue[y][x];

        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[row].length; col++) {
                double redDist = Math.abs(red[row][col] - thresholdR);
                double greenDist = Math.abs(green[row][col] - thresholdG);
                double blueDist = Math.abs(blue[row][col] - thresholdB);

                double overallDist = Math.sqrt( (redDist*redDist) + (greenDist*greenDist) + (blueDist*blueDist) );
                if (overallDist < maxDistance) {
                    red[row][col] = 255;
                    green[row][col] = 255;
                    blue[row][col] = 255;
                } else {
                    red[row][col] = 0;
                    green[row][col] = 0;
                    blue[row][col] = 0;
                }

            }
        }


        img.setColorChannels(red, green, blue);
        return img;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage original, DImage filtered) {
        x = mouseX;
        y = mouseY;
    }

    @Override
    public void keyPressed(char key) {

    }
}
