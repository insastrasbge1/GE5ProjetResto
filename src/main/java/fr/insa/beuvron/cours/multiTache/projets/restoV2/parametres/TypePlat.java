/*
Copyright 2000- Francois de Bertrand de Beuvron

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
package fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres;

import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.Utils;
import java.util.Arrays;

/**
 * Un type de Plat. null {@code défini par :
 * . un nom
 * . un tableau de temps de préparation :
 *    t[i] : temps pour préparer d'un coup i+1 sandwich
 * . un cout de préparation
 * . un prix de vente
 * . une durée maximale de conservation
 *
 * - tous les temps sont des entiers long en milli-secondes ms
 * - tous les prix/couts sont des entiers en centimes d'euro
 * }
 * 
 * @author francois
 */
public class TypePlat {

    /** nom du plat.
     * Note : informatif dans le logiciel : les types de plats seront identifiés
     * par leur position dans la carte.
     */
    private String nom;
    
    /**
     * temps necessaire pour produire n plats de ce type.
     * <pre> {@code
     * Il est en général plus rapide de produire d'un coup n plats de
     * même type que de produire n fois un plat de ce type.
     * la case N° i du tableau indique le temps necessaire pour produire d'un
     * coup i+1 plats de ce type
     * La taille du tableau fixe le nombre maximum de plats de ce type qui
     * peuvent être produit d'un coup. Plus précisément :
     * nbrMax = dureesPreparation.length + 1
     * } </pre>
     */
    private long[] dureesPreparation;
    
    /**
     * combien un plat de ce type coute à produire (cout des ingrédients)
     */
    private int coutPreparation;
    
    /**
     * combien un plat de ce type est vendu au client.
     */
    private int prixVente;
    
    /**
     * le plat doit être vendu au maximum tempsMaxConservation après sa (fin de) production.
     */
    private long tempsMaxConservation;

    public TypePlat() {

    }

    public TypePlat(String nom, long[] dureesPreparation, int coutPreparation, int prixVente, long tempsMaxConservation) {
        this.nom = nom;
        this.dureesPreparation = dureesPreparation;
        this.coutPreparation = coutPreparation;
        this.prixVente = prixVente;
        this.tempsMaxConservation = tempsMaxConservation;
    }

    @Override
    public String toString() {
        return "TypePlat{" + "nom=" + nom
                + ", dureesPreparation=" + Utils.affDurees(dureesPreparation)
                + ", coutPreparation=" + coutPreparation
                + ", prixVente=" + prixVente
                + ", tempsMaxConservation=" + tempsMaxConservation + '}';
    }
    
    
    /**
     * les frites peuvent être produites en grande quantité, mais ne se conservent pas bien.
     * @return 
     */
    public static TypePlat frites() {
        int maxPortions = 20;
        long[] durees = new long[maxPortions-1];
        durees[0] = Utils.minToMs(10);
        for(int i = 1 ; i < durees.length ; i ++) {
            durees[i] = durees[i-1] + 10 * 1000;
        }
        return new TypePlat("frites", durees,100, 300, Utils.minToMs(20));
    }
    
    /**
     * le temps de préparation des burger est dégressif ; ils se conservent moyennement bien
     */    
    public static TypePlat burger() {
        int maxPortions = 10;
        long[] durees = new long[maxPortions-1];
        durees[0] = Utils.minToMs(10);
        for(int i = 1 ; i < durees.length ; i ++) {
            durees[i] = (long) (durees[0]*(1+ 0.1*i)) ;
        }
        return new TypePlat("burger", durees,300, 500, Utils.minToMs(30));
    }

    /**
     * le temps de préparation des salade n'est que légèrement dégressif ; elles se conserve bien au frigo
     */    
    public static TypePlat salade() {
        int maxPortions = 10;
        long[] durees = new long[maxPortions-1];
        durees[0] = Utils.minToMs(10);
        for(int i = 1 ; i < durees.length ; i ++) {
            durees[i] = (long) (durees[0]*(1+0.9*i)) ;
        }
        return new TypePlat("salade", durees,200, 500, Utils.minToMs(30));
    }
    
    public static void main(String[] args) {
        System.out.println(frites());
        System.out.println(burger());
        System.out.println(salade());
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the dureesPreparation
     */
    public long[] getDureesPreparation() {
        return dureesPreparation;
    }

    /**
     * @return the coutPreparation
     */
    public int getCoutPreparation() {
        return coutPreparation;
    }

    /**
     * @return the prixVente
     */
    public int getPrixVente() {
        return prixVente;
    }

    /**
     * @return the tempsMaxConservation
     */
    public long getTempsMaxConservation() {
        return tempsMaxConservation;
    }

}
