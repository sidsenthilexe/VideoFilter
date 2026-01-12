package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

public class ClickThresholdFilter implements PixelFilter, Interactive {
    private int threshold;

    public ClickThresholdFilter() {
        threshold = 127;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] > threshold) {
                    grid[r][c] = 255;
                } else {
                    grid[r][c] = 0;
                }
            }
        }

        img.setPixels(grid);
        return img;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage original, DImage filtered) {
        short[][] grid = original.getBWPixelGrid();
        threshold = grid[mouseY][mouseX];
    }

    @Override
    public void keyPressed(char key) {
        if (key=='+') threshold+=10;
        if (key=='-') threshold-=10;
        threshold=Math.max(Math.min(threshold,255),0);
    }
}

