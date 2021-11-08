package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class DefaultTeam {
    public static final int K = 5;
    public ArrayList<ArrayList<Point>> calculKMeans(ArrayList<Point> points) {
        ArrayList<ArrayList<Point>> res = new ArrayList<>();
        ArrayList<Point> means = new ArrayList<>();

        initialization(points, means, res);
        firstAquisition(points, res, means);

        //Variables initialization for the loop
        boolean change = true;
        int nbIter = 0;

        //Re do Acquisition and Analyse until the groups do not change anymore
        while (change){
            nbIter++;
            analyse(res, means);
            change = acquisition(res, means);
        }
        System.out.println("Nb Iter : " + nbIter);
        return res;
    }

    private void firstAquisition(ArrayList<Point> points, ArrayList<ArrayList<Point>> res, ArrayList<Point> means) {
        for(Point p : points){
            if (means.contains(p)) continue;
            int min = -1;
            for (Point m : means){
                if (min == -1 || p.distance(m) < p.distance(means.get(min))){
                    min = means.indexOf(m);
                }
            }
            res.get(min).add(p);
        }
    }

    public void initialization(ArrayList<Point> points, ArrayList<Point> means, ArrayList<ArrayList<Point>> clusters) {
        Random r = new Random();
        for (int i = 0; i < K; i++) {
            int rdP = r.nextInt(points.size());
            means.add(points.get(rdP));
            clusters.add(new ArrayList<>());
            clusters.get(i).add(points.get(rdP));
            points.remove(rdP);
        }
    }

    public boolean acquisition(ArrayList<ArrayList<Point>> clusters, ArrayList<Point> means) {
        boolean change = false;
        for (int i = 0; i< clusters.size(); i++){
            for (int j = 0; j< clusters.get(i).size(); j++){
                int min = -1;
                for (int k = 0; k< means.size(); k++){
                    if (clusters.get(i).get(j).distance(means.get(k)) < clusters.get(i).get(j).distance(means.get(i))){
                        min = k;
                    }
                }
                if(min!=-1 && min!=i){
                    change = true;
                    Point p = clusters.get(i).remove(j);
                    j--;
                    clusters.get(min).add(p);

                }
            }
        }
        return change;
    }

    public void analyse(ArrayList<ArrayList<Point>> clusters, ArrayList<Point> means) {
        for (int i = 0; i < clusters.size(); i++) {
            Point bary = barycentre(clusters.get(i));
            means.set(i, bary);
        }
    }

    private Point barycentre(ArrayList<Point> points) {
        int x = 0;
        int y = 0;
        for (Point p : points) {
            x += p.x;
            y += p.y;
        }
        return new Point(x / points.size(), y / points.size());
    }

    public ArrayList<ArrayList<Point>> calculKMeansBudget(ArrayList<Point> points) {
        ArrayList<Point> rouge = new ArrayList<>();
        ArrayList<Point> verte = new ArrayList<>();

        for (int i = 0; i < points.size() / 2; i++) {
            rouge.add(points.get(i));
            verte.add(points.get(points.size() - i - 1));
        }
        if (points.size() % 2 == 1) rouge.add(points.get(points.size() / 2));

        ArrayList<ArrayList<Point>> kmeans = new ArrayList<>();
        kmeans.add(rouge);
        kmeans.add(verte);

        return kmeans;
    }
}
