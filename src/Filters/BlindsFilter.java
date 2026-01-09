package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;
import java.util.Arrays;

public class BlindsFilter implements PixelFilter {
private int width;
    public BlindsFilter() {
        String response= JOptionPane.showInputDialog("Enter Blind width");
        width=Integer.parseInt(response);

    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        width=Math.min(width, grid.length);

        int pixelCount = 0;
        boolean drawBlind = true;
        for (int row = 0; row < grid.length; row++) {
            if (drawBlind) Arrays.fill(grid[row], (short) 0);

            pixelCount++;
            if (pixelCount > width) {
                drawBlind = !drawBlind;
                pixelCount = 0;
            }
        }

        img.setPixels(grid);
        return img;
    }
}
