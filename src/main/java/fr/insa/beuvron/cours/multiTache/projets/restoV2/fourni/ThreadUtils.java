/*
 Copyright 2000-2014 Francois de Bertrand de Beuvron

 This file is part of CoursBeuvron.

 CoursBeuvron is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 CoursBeuvron is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni;

/**
 * quelques petites méthode statiques pour simplifier l'écriture des programmes.
 */
public class ThreadUtils {
    
    /**
     * un sleep qui ne doit pas être interrompu.
     * <p>
     * la methode {@link java.lang.Thread#sleep(long) } peut être interrompue 
     * générant un {@link java.lang.InterruptedException}. Cette exception doit
     * être traité (par exemple par un try-catch) ce qui alourdi l'écriture
     * dans le cas ou le sleep n'est pas censé être interrompu.
     * <p>
     * cette méthode fait simplement un sleep, et genere une {@link java.lang.Error}
     * si le sleep est interrompu
     * @param tempsEnMillisecondes 
     */
    public static void sleepSimple(long tempsEnMillisecondes) {
        try {
            Thread.sleep(tempsEnMillisecondes);
        } catch (InterruptedException ex) {
            throw new Error("unexpected interrupt",ex);
        }
    }
    
    /**
     * sleep simple qui dure un temps aléatoire t (en millisecondes)
     * tel que {@code 1 <= t <= maxMilliSecondes}
     * @param maxMillisecondes 
     */
    public static void sleepAleaMilliSecondes(long maxMillisecondes) {
        long duree = (long) (Math.random()*maxMillisecondes) + 1;
        sleepSimple(duree);
    }
    
    /**
     * renvoie un entier long e avec {@code min <= e <= max }.
     * @param min
     * @param max
     * @return 
     */
    public static long aleaBetween(long min,long max) {
        long delta = max - min;
        return min + (long) (Math.random() * (delta +1));
    }
    
    /**
     * sleep simple qui dure un temps aléatoire t (en millisecondes)
     * tel que {@code 1 <= t <= maxMilliSecondes}
     * @param minMillisecondes
     * @param maxMillisecondes 
     */
    public static void sleepAleaBetween(long minMillisecondes,long maxMillisecondes) {
        long duree = (long) (Math.random()*maxMillisecondes) + 1;
        sleepSimple(duree);
    }
    
    /**
     * sleep simple qui dure un temps aléatoire t qui est un nombre entier de seconde
     * tel que {@code 1 <= t <= maxSecondes}
     * @param maxSecondes 
     */
    public static void sleepAleaSecondes(int maxSecondes) {
        long duree = ((long) maxSecondes) * 1000;
        sleepAleaMilliSecondes(duree);
    }
    
    /**
     * un join qui ne doit pas être interrompu.voir {@link #sleepSimple(long)}
     * @param toJoin
     */
    public static void joinSansInterrupt(Thread toJoin) {
        try {
            toJoin.join();
        } catch (InterruptedException ex) {
            throw new Error("unexpected interrupt",ex);
        }
    }
    
}
