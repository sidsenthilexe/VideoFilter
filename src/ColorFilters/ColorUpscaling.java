package ColorFilters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;
import java.awt.*;

public class ColorUpscaling implements PixelFilter {

    int scaleFactor;

    public ColorUpscaling() {
        String response = JOptionPane.showInputDialog("Enter upscaling factor >1");
        scaleFactor = Integer.parseInt(response);
        scaleFactor = Math.max(scaleFactor, 2);

    }

    @Override
    public DImage processImage(DImage img) {
        short[][] r = img.getRedChannel();
        short[][] g = img.getGreenChannel();
        short[][] b = img.getBlueChannel();

        int h = img.getHeight();
        int w = img.getWidth();

        int newH = h * scaleFactor;
        int newW = w * scaleFactor;

        short[][] rNew = new short[newH][newW];
        short[][] gNew = new short[newH][newW];
        short[][] bNew = new short[newH][newW];



        img.setColorChannels(rNew, gNew, bNew);
        return img;
    }
}
