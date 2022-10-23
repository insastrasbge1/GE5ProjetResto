/*
    Copyright 2000-2014 Francois de Bertrand de Beuvron

    This file is part of UtilsBeuvron.

    UtilsBeuvron is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UtilsBeuvron is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UtilsBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.utils.probas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author francois
 */
public class TiragesAlea {

    /**
     * renvoie un tableau contenant taille entiers distincts compris entre min
     * et max. Attention : à n'utiliser que si max-min n'est pas trop grand : on
     * va créer la liste de tous les entiers compris entre min et max.
     */
    public static List<Integer> ensembleAleaEntier(int taille, int min, int max, Random r) {
        if (max - min + 1 < taille) {
            throw new Error("pas assez d'entiers possibles");
        }
        // note : je crée une nouvelle ArrayList car la methode collect de stream ne garanti pas
        // que la liste renvoyée est modifiable (et donc shuffle pas forcement possible)
        List<Integer> possibles = new ArrayList(IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList()));
        Collections.shuffle(possibles, r);
        return possibles.subList(0, taille);
    }
    
    public static int oneIntFromTab(int[] tab,Random r) {
        return tab[r.nextInt(tab.length)];
    }
    
    public static LocalDate dateAleaBetween(LocalDate min,LocalDate max, Random r) {
        long minDay = min.toEpochDay();
        long maxDay = max.toEpochDay();
        long delta = (long) (r.nextDouble()*(maxDay - minDay + 1));
        return min.plusDays(delta);
    }

    /**
     * Le poids représente une "probabilité" relative qu'un élément soit choisi.
     *
     * @param <E>
     */
    public static class ChoixPondere<E> {

        /**
         * @return the value
         */
        public E getValue() {
            return value;
        }

        /**
         * @return the poids
         */
        public double getPoids() {
            return poids;
        }

        private E value;
        private double poids;

        public ChoixPondere(E value, double poids) {
            this.value = value;
            this.poids = poids;
        }

        @Override
        public String toString() {
            return "{" + value + "[" + String.format("%.2g",poids) + "]}";
        }

        /**
         * @param value the value to set
         */
        public void setValue(E value) {
            this.value = value;
        }

        /**
         * @param poids the poids to set
         */
        public void setPoids(double poids) {
            this.poids = poids;
        }
    }

    /**
     * Associe les valeurs avec des poids pour pondérer un tirage aléatoire.
     *
     * @param <V>
     * @param valeurs
     * @param poids
     * @return
     */
    public static <V> List<ChoixPondere<V>> pondere(List<V> valeurs, List<Double> poids) {
        if (valeurs.size() != poids.size()) {
            throw new Error("les listes des valeurs et des poids doivent avoir la même taille");
        }
        List<ChoixPondere<V>> res = new ArrayList<ChoixPondere<V>>(valeurs.size());
        for (int i = 0; i < valeurs.size(); i++) {
            res.add(new ChoixPondere(valeurs.get(i), poids.get(i)));
        }
        return res;
    }

    /**
     * construit une liste de taille doubles compris entre min et max.
     *
     * @param taille
     * @param min
     * @param max
     * @return
     */
    public static List<Double> valsAleaBetween(int taille, double min, double max,Random r) {
        return r.doubles().limit(taille).boxed().collect(Collectors.toList());
    }
    
    public static <V> V choixAlea(List<V> valeurs,Random r) {
        return valeurs.get(r.nextInt(valeurs.size()));
    }

    public static <V> List<V> choixAleaMultiple(List<V> valeurs,int nbr,Random r) {
        ArrayList<V> copie = new ArrayList<>(valeurs);
        Collections.shuffle(copie,r);
        return copie.subList(0, nbr);
    }

    /**
     * construit une liste de choix pondérés aléatoirement par des poids compris
     * entre minPoids et maxPoids.
     *
     * @param <V>
     * @param valeurs
     * @param minPoids
     * @param maxPoids
     * @param r
     * @return
     */
    public static <V> List<ChoixPondere<V>> poidsAlea(List<V> valeurs, double minPoids, double maxPoids, Random r) {
        return pondere(valeurs, valsAleaBetween(valeurs.size(), minPoids, maxPoids,r));
    }

    /**
     * choisi aléatoirement une valeur vi en considérant que poidsi/somme(poids)
     * est la proba que vi soit choisi.
     *
     * @param choix
     * @return
     */
    public static <V> V choixAleaPondere(List<ChoixPondere<V>> choix, Random r) {
        double tot = choix.stream().map(cp -> cp.getPoids()).reduce(0.0, (a, b) -> a + b);
        int res = 0;
        double alea = r.nextDouble() * tot;
        double sum = choix.get(0).getPoids();
        while (res < choix.size() - 1 && alea > sum) {
            res++;
            sum = sum + choix.get(res).getPoids();
        }
        return choix.get(res).getValue();
    }

    private static void testAleaPondere(int nbrTirages, int nbrChoix, double minPoids, double maxPoids) {
        Random r = new Random();
        List<Integer> lesVals = IntStream.range(0, nbrChoix).boxed().collect(Collectors.toList());
        List<ChoixPondere<Integer>> poids = poidsAlea(lesVals, minPoids, maxPoids, r);
        System.out.println("poids : " + poids);
        double tot = poids.stream().map(cp -> cp.getPoids()).reduce(0.0, (a, b) -> a + b);
        int[] expected = new int[nbrChoix];
        for (int i = 0; i < nbrChoix; i++) {
            expected[i] = (int) (poids.get(i).getPoids() / tot * nbrTirages);
        }
        System.out.println("average expected : " + Arrays.toString(expected));
        int[] tirages = new int[nbrChoix];
        for (int i = 0; i < nbrTirages; i++) {
            tirages[choixAleaPondere(poids, r)]++;
        }
        System.out.println("tirages : " + Arrays.toString(tirages));
        int[] diffs = new int[nbrChoix];
        for (int i = 0; i < nbrChoix; i++) {
            diffs[i] = Math.abs(expected[i] - tirages[i]);
        }
        System.out.println("diffs : " + Arrays.toString(diffs));
    }

    /**
     * même chose que {@link #choixAleaPondere(java.util.List, java.util.Random)
     * } mais on en choisi nbrChoix et non plus un seul.
     */
    public static <V> List<V> choixMultiplesAleaPondere(int nbrChoix, List<ChoixPondere<V>> choix, Random r) {
        // copiée puisque modifiée
        if (nbrChoix > choix.size()) {
            throw new Error("Pas assez de choix !!");
        }
        List<ChoixPondere<V>> lp = new ArrayList<>(choix);
        List<V> res = new ArrayList<>(nbrChoix);
        for (int i = 0; i < nbrChoix; i++) {
            double tot = lp.stream().map(cp -> cp.getPoids()).reduce(0.0, (a, b) -> a + b);
            int c = 0;
            double alea = r.nextDouble() * tot;
            double sum = lp.get(0).getPoids();
            while (c < lp.size() - 1 && alea > sum) {
                c++;
                sum = sum + lp.get(c).getPoids();
            }
            res.add(lp.get(c).getValue());
            lp.remove(c);
        }
//        System.out.println("choix : " + Arrays.toString(res));
        return res;
    }

    private static void testChoixMultiplesAleaPondere() {
        Random r = new Random();
        int nm = 10;
        int nc = 3;
        int ne = 1000000;
        List<Integer> lesVals = IntStream.range(0, nm).boxed().collect(Collectors.toList());
        List<ChoixPondere<Integer>> poids = poidsAlea(lesVals, 1, 10, r);
        System.out.println("pondérations : " + poids);
        int[] rep = new int[nm];
        for (int i = 0; i < ne; i++) {
            List<Integer> choix = choixMultiplesAleaPondere(nc, poids, r);
            for (int c : choix) {
                rep[c]++;
            }
//            System.out.println(Arrays.toString(choix));
        }
        System.out.println("------- repartition : ");
        System.out.println(Arrays.toString(rep));
        double tot = poids.stream().map(cp -> cp.getPoids()).reduce(0.0, (a, b) -> a + b);
        int[] expected = new int[nm];
        for (int i = 0; i < nm; i++) {
            expected[i] = (int) (poids.get(i).getPoids() / tot * (ne * nc));
        }
        System.out.println("average expected : " + Arrays.toString(expected));
        int[] diffs = new int[nm];
        for (int i = 0; i < nm; i++) {
            diffs[i] = Math.abs(expected[i] - rep[i]);
        }
        System.out.println("diffs : " + Arrays.toString(diffs));

    }

    public static void main(String[] args) {
//        testAleaPondere(1000000, 10, 1, 1);
        testChoixMultiplesAleaPondere();
    }

}
