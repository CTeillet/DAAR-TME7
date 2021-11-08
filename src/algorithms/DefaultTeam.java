package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class DefaultTeam {
    public static final int k = 5;
    public ArrayList<ArrayList<Point>> calculKMeans(ArrayList<Point> points) {
        //1. Choisir K points aleatoires parmi points existants et le copier
        //2. Parcourir S et associer au points significatif le plus proche
        //3. Faire le barycentre pour les points significatif
        //4. Revenir étape 2
        ArrayList<ArrayList<Point>> res = new ArrayList<>();
        ArrayList<Point> means = new ArrayList<>();
        Random r = new Random();
        for (int i=0; i<k; i++){
            int rdP = r.nextInt(points.size());
            ArrayList<Point> temp = new ArrayList<>();
            temp.add(points.get(rdP));
            res.add(temp);
            means.add(points.get(rdP));
        }
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
        while (true){
            for (int i=0; i<k; i++){
                Point bary = barycentre(res.get(i));
                means.set(i, bary);
            }
            boolean change = false;
            for (int i = 0; i< res.size(); i++){
                for (int j = 0; j< res.get(i).size(); j++){
                    int min = -1;
                    for (int k = 0; k< means.size(); k++){
                        //System.out.println("I : "+i+" J : "+j+" K : "+k);
                        if (res.get(i).get(j).distance(means.get(k)) < res.get(i).get(j).distance(means.get(i))){
                            min = k;
                        }
                    }
                    if(min!=-1 && min!=i){
                        change = true;
                        Point p = res.get(i).remove(j);
                        j--;
                        res.get(min).add(p);

                    }
                }
            }
            if (!change) break;
        }


        return res;
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
        ArrayList<Point> rouge = new ArrayList<Point>();
        ArrayList<Point> verte = new ArrayList<Point>();

        for (int i = 0; i < points.size() / 2; i++) {
            rouge.add(points.get(i));
            verte.add(points.get(points.size() - i - 1));
        }
        if (points.size() % 2 == 1) rouge.add(points.get(points.size() / 2));

        ArrayList<ArrayList<Point>> kmeans = new ArrayList<ArrayList<Point>>();
        kmeans.add(rouge);
        kmeans.add(verte);

        /*******************
         * PARTIE A ECRIRE *
         *******************/
        return kmeans;
    }
}
