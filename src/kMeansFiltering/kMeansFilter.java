package kMeansFiltering;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;
import java.util.ArrayList;

public class kMeansFilter implements PixelFilter {

    private int k;

    public kMeansFilter() {
        String response = JOptionPane.showInputDialog(null, "Enter number of clusters");
        k = Integer.parseInt(response);
        k = Math.max(1, k);
    }

    @Override
    public DImage processImage(DImage img) {

        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        ArrayList<Point> points = new ArrayList<>();
        ArrayList<Cluster> clusters = new ArrayList<>();

        for (short i = 0; i < k; i++) {
            Cluster newCluster = new Cluster((short) (Math.random() * 255), (short) (Math.random()*255), (short) (Math.random()*255));
            clusters.add(newCluster);
        }

        for (int r = 0; r < red.length ; r++) {
            for (int c = 0; c < red[0].length; c++) {
                Point newPoint = new Point(red[r][c], green[r][c] ,blue[r][c]);
                newPoint.setCoords(r, c);
                points.add(newPoint);
            }
        }

        ArrayList<Point> clusterOldPoints = new ArrayList<>();
        ArrayList<Point> clusterNewPoints = new ArrayList<>();

        do {
            clusterOldPoints.clear();
            for (Cluster c : clusters) {
                clusterOldPoints.add(c.getCenterPoint());
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

            clusterNewPoints.clear();
            for (Cluster c : clusters) {
                c.moveToCenter();
                c.clearAll();
                clusterNewPoints.add(c.getCenterPoint());
            }
        } while (!clusterOldPoints.equals(clusterNewPoints));


    }
}
