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

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        short[][] filteredR = new short[red.length][red[0].length];
        short[][] filteredG = new short[green.length][green[0].length];
        short[][] filteredB = new short[blue.length][blue[0].length];


        filterColors(red, green, blue, filteredR, filteredG, filteredB, Colors.CARD);
        cards = findCardCorners(filteredR, filteredG, filteredB);

        img.setColorChannels(filteredR, filteredG, filteredB);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        drawCardCorners(window, cards);
    }

    private void filterColors(short[][] red, short[][] green, short[][] blue,
                            short[][] fRed, short[][] fGreen, short[][] fBlue, Colors color) {

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


    private ArrayList<Card> findCardCorners(short[][] red, short[][] green, short[][] blue) {

        boolean[] whiteCols = new boolean[red[0].length];
        boolean[] whiteRows = new boolean[red.length];

        for (int c = 0; c < red[0].length; c++) {
            int numWhite = 0;

            for (int r = 0; r < red.length; r++) {

                if (red[r][c] == Colors.CARD.R() &&
                    green[r][c] == Colors.CARD.G() &&
                    blue[r][c] == Colors.CARD.B()) {
                    numWhite ++;
                }
            }

            whiteCols[c] = numWhite > red[0].length / 3;
        }

        for (int r = 0; r < red.length; r++) {
            int numWhite = 0;
            for (int c = 0; c < red[0].length; c++) {

                if (red[r][c] == Colors.CARD.R() &&
                        green[r][c] == Colors.CARD.G() &&
                        blue[r][c] == Colors.CARD.B()) {
                    numWhite ++;
                }
            }

            whiteRows[r] = numWhite > red.length / 3;
        }

        int countTrueCols = 0;
        for (boolean c : whiteCols) {
            if (c) countTrueCols++;
        }

        int countTrueRows = 0;
        for (boolean r : whiteRows) {
            if (r) countTrueRows++;
        }

        System.out.println("True Cols: " + countTrueCols);
        System.out.println("True Rows: " + countTrueRows);

        ArrayList<Card> cards = getCards(whiteCols, whiteRows);

        System.out.println("Cards: " + cards.size());

        return cards;
    }

    private static ArrayList<Card> getCards(boolean[] whiteCols, boolean[] whiteRows) {
        ArrayList<Integer> cardStartsCol = new ArrayList<>();
        ArrayList<Integer> cardStartsRow = new ArrayList<>();
        ArrayList<Integer> cardEndsCol = new ArrayList<>();
        ArrayList<Integer> cardEndsRow = new ArrayList<>();

        for (int col = 1; col < whiteCols.length; col++) {
            if (whiteCols[col] && !whiteCols[col - 1]) { cardStartsCol.add(col); }
            if (!whiteCols[col] && whiteCols[col-1]) { cardEndsCol.add(col-1); }
        }

        for (int row = 1; row < whiteRows.length; row++) {
            if (whiteRows[row] && !whiteRows[row-1]) { cardStartsRow.add(row); }
            if (!whiteRows[row] && whiteRows[row-1]) { cardEndsRow.add(row-1); }
        }

        ArrayList<Card> cards = new ArrayList<>();

        for (int i = 0; i < cardStartsRow.size(); i++) {
            for (int j = 0; j < cardStartsCol.size(); j++) {
                cards.add(new Card(cardStartsRow.get(i), cardStartsCol.get(j), cardEndsRow.get(i), cardEndsCol.get(i)));
            }
        }
        return cards;
    }

    private void drawCardCorners(PApplet window, ArrayList<Card> cards) {
        window.fill( window.color(255, 0, 0) );
        window.stroke( window.color(255, 0, 0) );
        for (Card c : cards) {
            window.ellipse(c.getX1(), c.getY1(), 5, 5);
            window.ellipse(c.getX2(), c.getY2(), 5, 5);
        }

    }


}
