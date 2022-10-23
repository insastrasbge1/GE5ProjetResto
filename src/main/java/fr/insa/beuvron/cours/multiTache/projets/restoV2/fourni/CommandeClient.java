/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni;

import java.util.Arrays;

/**
 *
 * @author francois
 */
public class CommandeClient {
    
    /**
     * l'identificateur du client.
     */
    private int clientID;
    
    /**
     * la commande du client en nombre des divers plats à la carte.
     * On a commande.length == nombre de plats à la carte.
     */
    private int[] commande;

    public CommandeClient(int clientID, int[] commande) {
        this.clientID = clientID;
        this.commande = commande;
    }

    public int getClientID() {
        return clientID;
    }

    public int[] getCommande() {
        return commande;
    }

    @Override
    public String toString() {
        return "CommandeClient{" + clientID + " : " + Arrays.toString(commande) + '}';
    }
    
    
    
}
