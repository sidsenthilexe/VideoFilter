package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class BetterDownsamplingFilter implements PixelFilter {
    
    private int blockSize;
    
    public BetterDownsamplingFilter() {
        String response = JOptionPane.showInputDialog("Enter Block Size: ");
        blockSize = Integer.parseInt(response);
    }
    
    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        short[][] newGrid = new short[grid.length/blockSize][grid[0].length/blockSize];

        for (int row = 0; row < newGrid.length; row++) {
            for (int col = 0; col < newGrid[row].length; col++) {
                int sum = 0;

                for (int avgRow = row*blockSize; avgRow < blockSize+(row*blockSize); avgRow++) {
                    for (int avgCol = col*blockSize; avgCol < blockSize+(col*blockSize); avgCol++) {
                        sum += grid[avgRow][avgCol];
                    }
                }

                short avg = (short) (sum / (blockSize*blockSize));

                newGrid[row][col] = avg;


            }
        }

        img.setPixels(newGrid);
        return img;


        
    }
}
