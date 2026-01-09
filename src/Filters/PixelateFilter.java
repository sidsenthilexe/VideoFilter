package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class PixelateFilter implements PixelFilter {

    private int size;

    public PixelateFilter() {
        String response = JOptionPane.showInputDialog("Enter Block Size (Odd #s Only): ");
        size = Integer.parseInt(response);
        if (size%2 == 0) size++;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int row = 0; row < grid.length-size; row+=size) {
            for (int col = 0; col < grid[row].length-size; col+=size) {
                int centerDistance = (int)size/2;
                short centerColor=grid[row+centerDistance][col+centerDistance];
                for (int r2 = row; r2 < row+size; r2++) {
                    for (int c2 = col; c2 < col+size; c2++){
                        grid[r2][c2]=centerColor;
                    }
                }


            }
        }
        img.setPixels(grid);
        return img;
    }
}
