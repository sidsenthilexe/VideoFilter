package ColorFilters;

import Interfaces.PixelFilter;
import core.DImage;

public class SwapRG implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        img.setColorChannels(green, red, blue);
        return img;

    }
}
