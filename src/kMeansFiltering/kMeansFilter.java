package kMeansFiltering;

import Interfaces.PixelFilter;
import core.DImage;
import kMeansUtil.Cluster;
import kMeansUtil.Point;

import javax.swing.*;
import java.util.ArrayList;

public class kMeansFilter implements PixelFilter {

    private int k;
    private int update;
    private boolean filtered;
    private short[][] savedRed;
    private short[][] savedBlue;
    private short[][] savedGreen;

    public kMeansFilter() {
        String response = JOptionPane.showInputDialog(null, "Enter number of clusters");
        k = Integer.parseInt(response);
        k = Math.max(1, k);
        response = JOptionPane.showInputDialog(null, "1: Image should constantly update\n2: Image should update once");
        update = Integer.parseInt(response);
        update = (Math.min(2, Math.max(1, update)));
        filtered = false;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        if (!filtered) {
            ArrayList<Point> points = new ArrayList<>();
            ArrayList<Cluster> clusters = new ArrayList<>();

            for (short i = 0; i < k; i++) {
                Cluster newCluster = new Cluster((short) (Math.random() * 255), (short) (Math.random() * 255), (short) (Math.random() * 255));
                clusters.add(newCluster);
            }

            for (int r = 0; r < red.length; r++) {
                for (int c = 0; c < red[0].length; c++) {
                    Point newPoint = new Point(red[r][c], green[r][c], blue[r][c]);
                    newPoint.setCoords(r, c);
                    points.add(newPoint);
                }
            }

            ArrayList<Boolean> changes = new ArrayList<>();

            do {
                changes.clear();
                for (Cluster c : clusters) {
                    c.clearAll();
                }
                for (Point p : points) {
                    double smallestDistance = p.getDistTo(clusters.get(0).getCenterPoint());
                    Cluster closestCluster = clusters.get(0);

                    for (Cluster c : clusters) {
                        double dist = p.getDistTo(c.getCenterPoint());
                        if (dist < smallestDistance) {
                            smallestDistance = dist;
                            closestCluster = c;
                        }
                    }

                    closestCluster.addPoint(p);
                }

                for (Cluster c : clusters) {
                    changes.add(c.moveToCenter());
                }

            } while (changes.contains(true));

            // Set all point colors
            for (Cluster c : clusters) {
                ArrayList<Point> clusterPoints = c.getPoints();
                for (Point p : clusterPoints) {
                    red[p.getRow()][p.getCol()] = c.getCenterPoint().getR();
                    blue[p.getRow()][p.getCol()] = c.getCenterPoint().getB();
                    green[p.getRow()][p.getCol()] = c.getCenterPoint().getG();
                }
            }

            savedRed = red;
            savedBlue = blue;
            savedGreen = green;

            if (update == 2) filtered = true;

        } else {
            red = savedRed;
            blue = savedBlue;
            green = savedGreen;
        }

        img.setColorChannels(red, green, blue);
        return (img);
    }
}