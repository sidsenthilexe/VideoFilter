package cardRecognition;

import Interfaces.PixelFilter;
import cardRecognitionUtil.Constants;
import core.DImage;

public class CardFilter implements PixelFilter {

    // NOTE: Test image resize is 1000x750

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        short[][] filteredR = new short[red.length][red[0].length];
        short[][] filteredG = new short[green.length][green[0].length];
        short[][] filteredB = new short[blue.length][blue[0].length];

        filterColors(red, green, blue, filteredR, filteredG, filteredB, Constants.ColorThresholds.CARD);

        img.setColorChannels(filteredR, filteredG, filteredB);
        return img;
    }

    public void filterColors(short[][] red, short[][] green, short[][] blue,
                            short[][] fRed, short[][] fGreen, short[][] fBlue, Constants.ColorThresholds color) {

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[r].length; c++) {
                double redDist = Math.abs(red[r][c] - color.R());
                double greenDist = Math.abs(green[r][c] - color.G());
                double blueDist = Math.abs(blue[r][c] - color.B());

                double overallDist = Math.sqrt((redDist * redDist) + (greenDist * greenDist) + (blueDist * blueDist));

                if (overallDist < color.DIST()) {
                    fRed[r][c] = 255;
                    fGreen[r][c] = 255;
                    fBlue[r][c] = 255;
                } else {
                    fRed[r][c] = 0;
                    fGreen[r][c] = 0;
                    fBlue[r][c] = 0;
                }
            }
        }

    }

}
