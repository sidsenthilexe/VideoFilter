package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.Arrays;

public class LightenFilter implements PixelFilter {
    private int max;

    public LightenFilter() { max = 255; }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        System.out.println(Arrays.deepToString(grid));

        img.setPixels(grid);
        return img;
    }
}
