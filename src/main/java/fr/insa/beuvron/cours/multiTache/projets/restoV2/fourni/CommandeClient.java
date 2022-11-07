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
