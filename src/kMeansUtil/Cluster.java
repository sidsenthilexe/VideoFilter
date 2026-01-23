package kMeansUtil;

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

    public ArrayList<Point> getPoints() { return points; }

    public Point getCenterPoint() { return center;}

    public boolean moveToCenter() {
        short oldR = getCenterPoint().getR();
        short oldG = getCenterPoint().getG();
        short oldB = getCenterPoint().getB();

        double avgR = 0;
        double avgG = 0;
        double avgB = 0;

        for(Point p : points) {
            avgR += p.getR();
            avgG += p.getG();
            avgB += p.getB();
        }

        short newR = 0, newG = 0, newB = 0;

        if (points.isEmpty()) {
            newR = oldR;
            newG = oldG;
            newB = oldB;
        } else {
            newR = (short) (avgR / points.size());
            newG = (short) (avgG / points.size());
            newB = (short) (avgB / points.size());
        }

        center.setPosition(newR, newG, newB);

        return (oldR!=newR || oldG!=newG || oldB!=newB);
    }

}
