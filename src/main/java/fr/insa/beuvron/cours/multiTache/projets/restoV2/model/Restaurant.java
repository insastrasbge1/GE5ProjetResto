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
package fr.insa.beuvron.cours.multiTache.projets.restoV2.model;

/**
 * un restaurant.
 * {@code
 * défini par :
 * - une carte (type de sandwich proposés)
 * - une zone de stockage des sandwich
 *     !! -> la zone de stockage contient une sous-zone pour chaque type de
 *           sandwich proposés à la carte
 * - un nombre de caisses (nombre de client pouvant être simultanément en cours
 *   de commande)
 * - un nombre d'employés.
 * -
 * }
 * @author francois
 */
public class Restaurant {
    
    private Carte carte;
    private Stockage stockage;
    private int nbrEmployes;
    private int nbrCaisse;
    private long dureeCommande;
    private long dureeChargement;
    private long dureeDechargement;

    public Restaurant(Carte carte, Stockage stockage, int nbrEmployes, int nbrCaisse, long dureeCommande, long dureeChargement, long dureeDechargement) {
        if (carte.getLesPlats().length != stockage.getCapacites().length) {
            throw new Error("problème inconsistent : la taille des stockages doit être égal à la taille de la carte");
        }
        this.carte = carte;
        this.stockage = stockage;
        this.nbrEmployes = nbrEmployes;
        this.nbrCaisse = nbrCaisse;
        this.dureeCommande = dureeCommande;
        this.dureeChargement = dureeChargement;
        this.dureeDechargement = dureeDechargement;
    }

    /**
     * @return the carte
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * @return the stockage
     */
    public Stockage getStockage() {
        return stockage;
    }

    /**
     * @return the nbrEmployes
     */
    public int getNbrEmployes() {
        return nbrEmployes;
    }

    /**
     * @return the nbrCaisse
     */
    public int getNbrCaisse() {
        return nbrCaisse;
    }

    /**
     * @return the dureeCommande
     */
    public long getDureeCommande() {
        return dureeCommande;
    }

    /**
     * @return the dureeChargement
     */
    public long getDureeChargement() {
        return dureeChargement;
    }

    /**
     * @return the dureeDechargement
     */
    public long getDureeDechargement() {
        return dureeDechargement;
    }
    
    
}
