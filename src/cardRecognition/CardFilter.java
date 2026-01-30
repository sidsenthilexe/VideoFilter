package cardRecognition;

import Interfaces.PixelFilter;
import cardRecognitionUtil.Constants;
import cardRecognitionUtil.Constants.Colors;
import core.DImage;
import cardRecognitionUtil.Card;

import java.util.ArrayList;

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

        ArrayList<Card> cards = new ArrayList<>();

        filterColors(red, green, blue, filteredR, filteredG, filteredB, Colors.CARD);
        cards = findCardCorners(filteredR, filteredG, filteredB);

        img.setColorChannels(filteredR, filteredG, filteredB);
        return img;
    }

    public void filterColors(short[][] red, short[][] green, short[][] blue,
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

    public ArrayList<Card> findCardCorners(short[][] red, short[][] green, short[][] blue) {

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

            whiteCols[c] = numWhite > red.length / 2;

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

            whiteRows[r] = numWhite > red.length / 2;
        }

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
            Card newCard = new Card(cardStartsRow.get(i), cardStartsCol.get(i), cardEndsRow.get(i), cardEndsCol.get(i));
            cards.add(newCard);
        }

        return cards;
    }

}
