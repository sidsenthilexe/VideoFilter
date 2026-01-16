package ColorFilters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;
import java.awt.*;

public class HSLColorMask implements PixelFilter, Interactive {
    private int x, y;
    private double maxDistance, thresholdHue;

    public HSLColorMask() {
        x = 0;
        y = 0;

        String response = JOptionPane.showInputDialog(null, "Enter threshold hue distance");

        maxDistance = Double.parseDouble(response.trim());
        // circular hue distances range 0..180 (e.g., 190 degrees is equivalent to 170)
        maxDistance = Math.min(180, (Math.max(0, maxDistance)));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        int thresholdR = red[y][x];
        int thresholdG = green[y][x];
        int thresholdB = blue[y][x];

        float[] thresholdHueOut = Color.RGBtoHSB(thresholdR, thresholdG, thresholdB, null);
        thresholdHue = thresholdHueOut[0] * 360;

        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[row].length; col++) {

                int r = red[row][col];
                int g = green[row][col];
                int b = blue[row][col];

                float[] pixelHueOut = Color.RGBtoHSB(r, g, b, null);
                double pixelHue = pixelHueOut[0] * 360;

                double overallDist = hueDistance(thresholdHue, pixelHue);

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

    private double hueDistance(double h1, double h2) {
        if (Double.isNaN(h1) || Double.isNaN(h2)) return 360;
        double d = Math.abs(h1 - h2);
        return Math.min(d, 360 - d);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage original, DImage filtered) {
        x = mouseX;
        y = mouseY;
        System.out.println(thresholdHue);
    }

    @Override
    public void keyPressed(char key) {
        if (key == '+') maxDistance+=10;
        if (key == '-') maxDistance-=10;
        // keep within 0..180
        maxDistance = Math.min(180, (Math.max(0, maxDistance)));
    }
}
