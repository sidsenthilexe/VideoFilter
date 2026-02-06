package cardRecognition;

import Interfaces.Drawable;
import Interfaces.PixelFilter;
import cardRecognitionUtil.Constants.Colors;
import core.DImage;
import cardRecognitionUtil.Card;
import processing.core.PApplet;

import java.util.ArrayList;

public class CardFilter implements PixelFilter, Drawable {

    // NOTE: Test image resize is 1000x750
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Integer> topLeftX = new ArrayList<>();
    ArrayList<Integer> topLeftY = new ArrayList<>();
    ArrayList<Integer> bottomRightX = new ArrayList<>();
    ArrayList<Integer> bottomRightY = new ArrayList<>();


    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();


        short[][] filteredR = new short[red.length][red[0].length];
        short[][] filteredG = new short[green.length][green[0].length];
        short[][] filteredB = new short[blue.length][blue[0].length];


        short[][] bwGrid = filterColors(red, green, blue, filteredR, filteredG, filteredB, Colors.CARD);

        findCorners(bwGrid);

        img.setColorChannels(filteredR, filteredG, filteredB);
        return img;
    }

    private void findCorners(short[][] grid) {

        topLeftX.clear();
        topLeftY.clear();
        bottomRightX.clear();
        bottomRightY.clear();

        int searchSize = 25;

        for (int r = searchSize; r + searchSize < grid.length; r++) {
            for (int c = searchSize; c + searchSize < grid[0].length; c++) {

                boolean surroundingColors = true;

                for (int i = 1; i <= searchSize; i++) {

                    if (grid[r-i][c] != 0) surroundingColors = false;
                    if (grid[r][c-i] != 0) surroundingColors = false;
                    if (grid[r-i][c-i] != 0) surroundingColors = false;
                }

                for (int i = 1; i <= searchSize; i++) {
                    if (grid[r+i][c] != 255) surroundingColors = false;
                    if (grid[r][c+i] != 255) surroundingColors = false;
                    if (grid[r+i][c+i] != 255) surroundingColors = false;
                }

                boolean checkNear = true;
                double maxDist = 100d;
                for (int i = 0; i < topLeftX.size(); i++) {
                    int x = (topLeftX.get(i) - r);
                    int y = (topLeftY.get(i) - c);
                    double distance = Math.sqrt(x * x + y * y);
                    if (distance <= maxDist) {
                        checkNear = false;
                        break;
                    }
                }

                if (grid[r][c] == 255 && surroundingColors && checkNear) {
                    topLeftX.add(r);
                    topLeftY.add(c);
                }
            }
        }

        for (int r = searchSize; r + searchSize< grid.length; r++) {
            for (int c = searchSize; c + searchSize < grid[0].length; c++) {

                boolean surroundingColors = true;

                for (int i = 1; i <= searchSize; i++) {


                    if (grid[r+i][c] != 0) surroundingColors = false;
                    if (grid[r][c+i] != 0) surroundingColors = false;
                    if (grid[r+i][c+i] != 0) surroundingColors = false;
                }

                for (int i = 1; i <= searchSize; i++) {
                    if (grid[r-i][c] != 255) surroundingColors = false;
                    if (grid[r][c-i] != 255) surroundingColors = false;
                    if (grid[r-i][c-i] != 255) surroundingColors = false;
                }

                boolean checkNear = true;
                double maxDist = 100d;
                for (int i = 0; i < bottomRightX.size(); i++) {
                    int x = (bottomRightX.get(i) - r);
                    int y = (bottomRightY.get(i) - c);
                    double distance = Math.sqrt(x * x + y * y);
                    if (distance <= maxDist) {
                        checkNear = false;
                        break;
                    }
                }

                if (grid[r][c] == 255 && surroundingColors && checkNear) {
                    bottomRightX.add(r);
                    bottomRightY.add(c);
                }
            }
        }


    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        for (int i = 0; i < topLeftX.size(); i++) {
            window.stroke(255,0,0);
            window.fill(255,0,0);
            window.ellipse(topLeftY.get(i), topLeftX.get(i), 5,5);
            System.out.println(topLeftX.size());
        }

        for (int i = 0; i < bottomRightX.size(); i++) {
            window.stroke(255, 0, 0);
            window.fill(255, 0, 0);
            window.ellipse(bottomRightY.get(i), bottomRightX.get(i), 5, 5);
        }
    }

    private short[][] filterColors(short[][] red, short[][] green, short[][] blue,
                            short[][] fRed, short[][] fGreen, short[][] fBlue, Colors color) {

        short[][] bwGrid = new short[red.length][red[0].length];

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
                    bwGrid[r][c] = 255;
                } else {
                    fRed[r][c] = 0;
                    fGreen[r][c] = 0;
                    fBlue[r][c] = 0;
                    bwGrid[r][c] = 0;
                }
            }
        }

        return bwGrid;

    }



}
