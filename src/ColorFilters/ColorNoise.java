package ColorFilters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class ColorNoise implements PixelFilter {
    private double n;
    public ColorNoise() {
        String response = JOptionPane.showInputDialog(null, "Enter a noise probability (0-1)");
        n = Double.parseDouble(response);
        n = Math.min(1, (Math.max(0, n)));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[row].length; col++) {
                if (Math.random() < n) {
                    red[row][col] = (short)(Math.random() * 256);
                    green[row][col] = (short)(Math.random() * 256);
                    blue[row][col] = (short)(Math.random() * 256);
                }
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }
}
