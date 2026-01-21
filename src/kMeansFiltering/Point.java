package kMeansFiltering;

public class Point {
    private short r, g, b;

    private int row, col;

    public Point(short r, short g, short b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.row = 0;
        this.col = 0;
    }

    public void setCoords(int row, int col) {
        this.row = row;
        this.col = col;
    }



    public short getR() { return r; }

    public short getG() { return g; }

    public short getB() { return b; }

    public void setPosition(short r, short g, short b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getDistTo(Point p) {
        short dR = (short) Math.abs(r - p.getR());
        short dG = (short) Math.abs(g - p.getG());
        short dB = (short) Math.abs(b - p.getB());

        return Math.sqrt(dR*dR + dG*dG + dB*dB);
    }

}
