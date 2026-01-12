package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class SimpleUpsampling implements PixelFilter {
    private int samplingFactor, newWidth, newHeight;

    public SimpleUpsampling() {
        String response = JOptionPane.showInputDialog("Enter Super Sampling Multiplier (>1):");
        samplingFactor = Integer.parseInt(response);
        samplingFactor = Math.max(samplingFactor, 1);

    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        newHeight = grid.length * (samplingFactor);
        newWidth = grid[0].length * (samplingFactor);
        short[][] newGrid = new short[newHeight][newWidth];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                short pixelVal=grid[row][col];
                for (int r2 = row* samplingFactor; r2 < (row*samplingFactor) + samplingFactor; r2++) {
                    for (int c2 = col* samplingFactor; c2 < (col*samplingFactor) + samplingFactor; c2++) {
                        newGrid[r2][c2]=pixelVal;
                    }
                }
            }
        }

        img.setPixels(newGrid);
        return img;
    }
}
