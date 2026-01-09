package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.Arrays;

public class LightenFilter implements PixelFilter {
    private final short max;

    public LightenFilter() { max = 255; }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                short val = grid[row][col];
                short dist = (short) (max - val);
                if(dist > 0) dist = (short) (dist/2);
                grid[row][col] = (short) (max-dist);
            }
        }

        img.setPixels(grid);
        return img;
    }
}
