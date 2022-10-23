/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.beuvron.utils.probas;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * calcul de variables aléatoires pour certaines lois de distributions
 * courantes.
 *
 * Les principes sont tirés de Emmanuel Jeandel "Méthodes probabilistes en
 * informatique" http://www.loria.fr/~ejeandel/files/Proba/cours.pdf
 * (aussi dans dossier biblio de mes cours)
 *
 * pour un package plus complet, voir JDistlib :
 * http://jdistlib.sourceforge.net/ http://sourceforge.net/projects/jdistlib/
 * 
 * mais aussi apache commons math, en particulier le package
 * org.apache.commons.math3.distribution
 * https://commons.apache.org/proper/commons-math/javadocs/api-3.3/org/apache/commons/math3/distribution/package-summary.html
 * sur maven : 
 * https://mvnrepository.com/artifact/org.apache.commons/commons-math3
 * 
 *
 * @author fdebertranddeb01
 */
public class CalculsDirectDistributions {

    private Random rand;

    public CalculsDirectDistributions(Random rand) {
        this.rand = rand;
    }

    public CalculsDirectDistributions(long seed) {
        this(new Random(seed));
    }

    public CalculsDirectDistributions() {
        this(new Random());
    }

    /**
     * loi uniforme {@code 0 <= x < 1}
     *
     * @return x
     */
    public double loiUniforme() {
        return this.rand.nextDouble();
    }
    
    /**
     * loi uniforme {@code min <= x < max}
     *
     * @return x
     */
    public double loiUniformeBetween(double min,double max) {
        return this.loiUniforme()*(max-min)+min;
    }
    
    

    /**
     * voir {@link https://fr.wikipedia.org/wiki/Loi_de_Bernoulli} renvoie 1
     * avec proba p 0 avec proba (1-p)
     *
     * @param p proba de 1
     * @return 0 ou 1
     */
    public int loiBernouilli(double p) {
        if (this.loiUniforme() < p) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * loi Binomiale : somme de n tirages de Bernouilli.
     *
     * @param n nombre d'experiences
     * @param p proba qu'une exeperience soit positive
     * @return nombre (aleatoire) d'exeriences positive
     */
    public int loiBinomiale(int n, double p) {
        int somme = 0;
        for (int i = 0; i < n; i++) {
            somme = somme + loiBernouilli(p);
        }
        return somme;
    }
    
     
    /**
     * probas explicites.
     * 
     * @param probas probas[i] = proba de choisir l'entier i
     * @return un entier entre 0 et probas.length-1
     */
    public int loiProbaExplicite(double[] probas) {
        double alea = this.rand.nextDouble();
        int i = 0;
        double somme = probas[0];
        while (alea > somme) {
            i ++;
            somme = somme + probas[i];
        }
        return i;
    }

    /**
     * tirage sur loi exponentielle. (d'après wikipedia
     * https://fr.wikipedia.org/wiki/Loi_exponentielle )
     * <pre>
     * Si X est une variable aléatoire définissant la durée de vie d'un phénomène.
     * - la durée de vie moyenne est E(X)
     * - cette durée ne dépend pas du temps déjà écoulé :
     *      {@code p(X>s+t / X>s) = p(X>t) }
     * Alors X suit une densité de probabilité f :
     *      {@code f(t) = 0 si t < 0 }
     *      {@code f(t) = 1/E(X) exp(-t / E(X)) pour t >= 0 }
     * On pose en général lambda = 1/E(X) ce qui donne :
     *      {@code f(t) = lambda exp(-lambda t)}
     *
     * On peut calculer un tirage aléatoire de X suivant une loi exponentielle
     * à partir d'une variable U suivant uniforme grace à la formule :
     *      {@code X = -1/lambda Ln(U) }
     * Note : dans le cas ou U est nul, on refait le tirage
     * </pre>
     *
     * @param lambda
     * @return
     */
    public double loiExponentielle(double lambda) {
        double uniforme = this.loiUniforme();
        while (uniforme == 0) {
            uniforme = this.loiUniforme();
        }
        return -1 / lambda * Math.log(uniforme);
    }
    
    public double loiNormaleCentreeReduite() {
        double u1 = this.loiUniforme();
        double u2 = this.loiUniforme();
        return Math.sqrt(-2*Math.log(u1))*Math.cos(2*Math.PI*u2);
        
    }
    
    public double loiNormale(double moyenne,double sigma) {
        return loiNormaleCentreeReduite() * sigma + moyenne;
    }


    public static void testExponentielle(double moyenne, int nbrTirages, int tailleHisto, double xMaxDansHisto) {
        final CalculsDirectDistributions cal = new CalculsDirectDistributions();
        testLoi(() -> cal.loiExponentielle(1/moyenne), nbrTirages, tailleHisto, 0, xMaxDansHisto);
    }

    
    public static void testNormale(int nbrTirages, int tailleHisto, double xMaxDansHisto) {
        final CalculsDirectDistributions cal = new CalculsDirectDistributions();
        testLoi(() -> cal.loiNormaleCentreeReduite(), nbrTirages, tailleHisto, -xMaxDansHisto, xMaxDansHisto);
    }

    

    public static void testLoi(Callable<Double> loi, int nbrTirages, int tailleHisto, double xMinHisto, double xMaxHisto) {
        final CalculsDirectDistributions cal = new CalculsDirectDistributions();
        double somme = 0;
        double min = -1;
        double max = -1;
        double percent = (xMaxHisto - xMinHisto) / tailleHisto;
        for (int i = 0; i < nbrTirages; i++) {
            double x;
            try {
                x = loi.call();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            somme = somme + x;
            if (min == -1 || x < min) {
                min = x;
            }
            if (max == -1 || x > max) {
                max = x;
            }
        }
        System.out.println("moyenne : " + (somme / nbrTirages));
        System.out.println("min : " + min);
        System.out.println("max : " + max);
        dessineHisto(histoLoi(loi, nbrTirages, tailleHisto, xMinHisto, xMaxHisto), 80);
    }

    /**
     * effectue des tirages aleatoire d'une loi, et conserve les tirages sous
     * forme d'histogramme.
     *
     * <pre>
     * les tirages {@code x < valMin et x >= valMax} sont ignorés
     * chaque "case" i de l'histogramme contient le nombre de tirages x tels que
     *     {@code caseMin[i] <= x < caseMax[i]} avec
     *     {@code delta = (valMax - valMin) / tailleHisto}
     *     {@code caseMin = valMin + i * delta}
     *     {@code caseMax = valMin + (i+1) * delta}
     * </pre>
     *
     * @param loi une fonction qui retourne la valeur aleatoire
     * @param nbrTirages
     * @param tailleHisto
     * @param valMin
     * @param valMax
     * @return l'histogramme
     */
    public static int[] histoLoi(Callable<Double> loi, int nbrTirages, int tailleHisto, double valMin, double valMax) {
        CalculsDirectDistributions cal = new CalculsDirectDistributions();
        int[] histo = new int[tailleHisto];
        double delta = (valMax - valMin) / tailleHisto;
        for (int i = 0; i < nbrTirages; i++) {
            double x;
            try {
                x = loi.call();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (x >= valMin && x < valMax) {
                int n = (int) ((x - valMin) / delta);
                histo[n]++;
            }

        }
        return histo;
    }

    public static String multString(String s, int n) {
        StringBuilder sb = new StringBuilder(s.length() * n);
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void dessineHisto(int[] histo, int maxCol) {
        int repMax = 0;
        for (int i = 0; i < histo.length; i++) {
            if (histo[i] > repMax) {
                repMax = histo[i];
            }
        }
        for (int i = 0; i < histo.length; i++) {
            System.out.println(multString("*", histo[i] * maxCol / repMax));
        }

    }

    public static void main(String[] args) {
        testExponentielle(1, 100000, 30, 5);
        testNormale(100000, 50, 3);

    }

}
