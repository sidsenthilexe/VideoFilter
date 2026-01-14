package ColorFilters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class WeightedColorNoise implements PixelFilter {
    private double n;
    private double distance;
    public WeightedColorNoise() {
        String response = JOptionPane.showInputDialog(null, "Enter a noise probability (0-1)");
        n = Double.parseDouble(response);
        n = Math.min(1, (Math.max(0, n)));

        String response2 = JOptionPane.showInputDialog(null, "Enter the noise distance (0 - 255)");
        distance = Double.parseDouble(response2);
        distance = Math.min(255, Math.max(0, distance));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[row].length; col++) {
                if (Math.random() < n) {

                    red[row][col] = (short) Math.max(0, (Math.min(255, red[row][col] + (short)((Math.random() * distance)-(distance/2)))));
                    green[row][col] = (short) Math.max(0, (Math.min(255, green[row][col] + (short)((Math.random() * distance)-(distance/2)))));
                    blue[row][col] = (short) Math.max(0, (Math.min(255, blue[row][col] + (short)((Math.random() * distance)-(distance/2)))));
                }
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }
}
