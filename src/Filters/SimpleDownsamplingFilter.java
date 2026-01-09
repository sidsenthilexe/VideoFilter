package Filters;

import Interfaces.PixelFilter;
import core.DImage;


public class SimpleDownsamplingFilter implements PixelFilter {

    public SimpleDownsamplingFilter() { }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        short[][] newGrid = new short[grid.length/2][grid[0].length/2];

        for (int row = 0; row < newGrid.length; row++) {
            for (int col = 0; col < newGrid[0].length; col++) {
                newGrid[row][col]=grid[row*2][col*2];
            }
        }
        img.setPixels(newGrid);
        return img;
    }
}
