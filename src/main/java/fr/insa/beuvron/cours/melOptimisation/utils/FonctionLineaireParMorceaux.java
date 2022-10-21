/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.beuvron.cours.melOptimisation.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Une fonction définie par un ensemble de points.
 *
 * <pre> {@code
 * soit les points (Xi,Yi), tels que Xi != Xj pour tout i != j
 * On classe par Xi croissants ==> liste de points
 * [(X0,Y0),(X1,Y1), ... , (Xn,Yn)] avec i > j ==> Xi > Xj
 *
 * Cette liste représente une fonction f :
 * f(x) = 0 pour x < X1
 * f(x) = 0 pour x > Xn
 * f(x) = Yi + (x - Xi)/(X(i+1)-Xi) * (Y(i+1)-Yi) pour Xi <= x < X(i+1)
 * } </pre>
 *
 * @author fdebertranddeb01
 */
public class FonctionLineaireParMorceaux {

    private List<PointFLM> points;
    
    public List<PointFLM> getPoints() {
        return Collections.unmodifiableList(this.points);
    }

    /**
     *
     * @param points
     */
    public FonctionLineaireParMorceaux(List<PointFLM> points) {
        if (points.size() < 2) {
            throw new Error("Au moins 2 points !!");
        }
        List<PointFLM> ptries = new ArrayList<>(points);
        Collections.sort(points, PointFLM.XCOMPARATOR);
        this.points = ptries;
    }

    private static List<PointFLM> fromDoubles(double[][] points) {
        return new ArrayList<>(Arrays.stream(points, 0, points.length).map((t) -> {
            return new PointFLM(t[0], t[1]);
        }).toList());
    }

    /**
     *
     * @param points
     */
    public FonctionLineaireParMorceaux(double[][] points) {
        this(fromDoubles(points));
    }

    private int getIntervalleContenant(double x) {
        if (x < this.points.get(0).getX()) {
            return -1;
        } else if (x >= this.points.get(this.points.size() - 1).getX()) {
            return -2;
        } else {
            int i = 0;
            while (x >= this.points.get(i + 1).getX()) {
                i++;
            }
            return i;
        }
    }

    private double pxi(int i) {
        return this.points.get(i).getX();
    }

    private double pyi(int i) {
        return this.points.get(i).getY();
    }

    private double pxMin() {
        return pxi(0);
    }

    private double pxMax() {
        return pxi(this.points.size() - 1);
    }

    /**
     * valeur de la flm en x.
     *
     * @param x
     * @return f(x)
     */
    public double valeurEn(double x) {
        int inter = this.getIntervalleContenant(x);
        if (inter < 0) {
            return 0;
        } else {
            double xi = this.pxi(inter);
            double yi = this.pyi(inter);
            double xi1 = this.pxi(inter + 1);
            double yi1 = this.pyi(inter + 1);
            return yi + (x - xi) / (xi1 - xi) * (yi1 - yi);
        }
    }

    private static double integraleTrapeze(
            double x1, double y1,
            double x2, double y2) {
        return (x2 - x1) * (y1 + y2) / 2;
    }

    /**
     * integrale de la flm entre x1 et x2 {@code x1 <= x2}
     *
     * @param x1
     * @param x2
     * @return integrale(x1 à x2;f(x))
     */
    public double integrale(double x1, double x2) {
        if (x1 > x2) {
            throw new Error("x2 doit être >= à x1");
        } else if (x1 == x2) {
            return 0;
        } else if (x2 <= this.pxMin()) {
            return 0;
        } else if (x1 >= this.pxMax()) {
            return 0;
        } else {
            int i1 = this.getIntervalleContenant(x1);
            if (i1 == -2) {
                // intervalle hors domaine
                return 0;
            } else {
                if (i1 == -1) {
                    // avant premier intervalle
                    // equivalent à partir du premier point
                    i1 = 0;
                    x1 = this.pxi(0);
                }
                int i2 = this.getIntervalleContenant(x2);
                if (i1 == i2) {
                    // x1 et x2 dans le même intervalle
                    return integraleTrapeze(x1, this.valeurEn(x1),
                            x2, this.valeurEn(x2));
                } else {
                    double partielInf = integraleTrapeze(
                            x1, this.valeurEn(x1),
                            this.pxi(i1 + 1), this.pyi(i1 + 1));
                    int i2bis = i2;
                    if (i2 == -2) {
                        i2bis = this.points.size() - 2;
                    }
                    double complets = 0;
                    for (int i = i1 + 1; i < i2bis; i++) {
                        complets = complets + integraleTrapeze(
                                this.pxi(i), this.pyi(i),
                                this.pxi(i + 1), this.pyi(i + 1));
                    }
                    double partielSup;
                    if (i2 == -2) {
                        partielSup = 0;
                    } else {
                        partielSup = integraleTrapeze(
                                this.pxi(i2), this.pyi(i2),
                                x2, this.valeurEn(x2));
                    }
                    return partielInf + complets + partielSup;
                }

            }
        }
    }

    /**
     *
     * @param xmin
     * @param xmax
     * @param nbrInter
     * @return
     */
    public static FonctionLineaireParMorceaux flmSinus(double xmin, double xmax, int nbrInter) {
        double delta = (xmax - xmin) / nbrInter;
        List<PointFLM> points = new ArrayList<>();
        double curX = xmin;
        points.add(new PointFLM(curX, Math.sin(curX)));
        for (int i = 0; i < nbrInter; i++) {
            curX = curX + delta;
            points.add(new PointFLM(curX, Math.sin(curX)));
        }
        return new FonctionLineaireParMorceaux(points);
    }

    /** todoDoc. */
    public static void testIntegrale() {
        FonctionLineaireParMorceaux flm = flmSinus(0, 2 * Math.PI, 1000);
        double xmin = 0;
        double xmax = Math.PI;
        System.out.println("integrale flm sin(x) entre " + xmin + " et " + xmax + " = "
                + flm.integrale(xmin, xmax));
        xmin = -1;
        xmax = Math.PI;
        System.out.println("integrale flm sin(x) entre " + xmin + " et " + xmax + " = "
                + flm.integrale(xmin, xmax));
        xmin = Math.PI;
        xmax = 8;
        System.out.println("integrale flm sin(x) entre " + xmin + " et " + xmax + " = "
                + flm.integrale(xmin, xmax));
        xmin = -1;
        xmax = 8;
        System.out.println("integrale flm sin(x) entre " + xmin + " et " + xmax + " = "
                + flm.integrale(xmin, xmax));
        double tolerance = 1E-3;
        int nbrTest = 100000;
        double erreurMax = 0;
        for (int i = 0; i < nbrTest; i++) {
            double x1 = Math.random() * 4 * Math.PI - Math.PI;
            double x2 = Math.random() * 4 * Math.PI - Math.PI;
            if (x1 <= x2) {
                xmin = x1;
                xmax = x2;
            } else {
                xmin = x2;
                xmax = x1;
            }
            double approx = flm.integrale(xmin, xmax);
            double expected = -Math.cos(Math.max(0, Math.min(2 * Math.PI, xmax))) + Math.cos(Math.max(0, Math.min(2 * Math.PI, xmin)));
            double erreur = Math.abs(approx - expected);
            if (erreur > erreurMax) {
                erreurMax = erreur;
            }
            if (erreur > tolerance) {
                System.out.println("bad for " + xmin + " ; " + xmax + " : "
                        + "approx = " + approx + " expected = " + expected);
            }
        }
        System.out.println(nbrTest + " tests pour -PI <= xmin <= xmax < 3.PI : erreur Max : " + erreurMax);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        testIntegrale();
    }
}
