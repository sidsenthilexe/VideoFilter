package kMeansFiltering;

import java.util.ArrayList;

public class Cluster {
    private Point center;
    private ArrayList<Point> points;

    public Cluster(short r, short g, short b) {
        center = new Point(r, g, b);
        points = new ArrayList<>();
    }

    public void addPoint(Point p) { points.add(p); }

    public void removePoint(Point p) { points.remove(p); }

    public void clearAll() { points.clear(); }
    public Point getCenterPoint(){return center;}

    public void moveToCenter() {
        int avgR = 0;
        int avgG = 0;
        int avgB = 0;

        for(Point p : points) {
            avgR += p.getR();
            avgG = p.getG();
            avgB = p.getB();
        }

        short newR = (short) (avgR/(points.size()-1));
        short newG = (short) (avgG/(points.size()-1));
        short newB = (short) (avgB/(points.size()-1));

        center.setPosition(newR, newG, newB);
    }

}
