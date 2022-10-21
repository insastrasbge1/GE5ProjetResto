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

import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;

/**
 * données de simulation.
 * <pre> {@code 
 * - la cadence d'arrivée des clients est variable en fonction de l'heure.
 * Nous simulons cela avec une fonction tempsMoyenAvantProchainClient = f(alpha)
 * ou alpha est le pourcentage de temps écoulé.
 * Le temps effectif avant le prochain client est calculé par une loi exponentielle
 * de paramètre tempsMoyenAvantProchainClient
 * - les commandes des clients dépendent de l'heure : 
 *   . le nombre de plats commandé dépend de l'heure
 *   . certains plats sont plus ou moins commandés en fonction de l'heure
 * Pour simuler cela, nous utilisons :
 * une fonction nbrMoyenPlatCommande = f(alpha)
 * Le nombre réel de plats commandés est calculé par une loi binomiale de paramètres
 *  n = 2 * nbrMoyenPlatCommande et p = 0.5
 * Ensuite nous avons pour chaque plat pi possible une fonction
 *   ponderationDemande(pi) = f(alpha)
 * pour normaliser, nous calculons :
 *   total = somme(pi ; ponderationDemande(pi))
 * Pour chaque plat commandé, la probabilité de choisir pi est égal à 
 *   ponderationDemande(pi)/total
 * } </pre>
 * @author francois
 */
public class Simulation {
    
    private Restaurant resto;
    /**
     * temps (simulation) entre l'ouverture du restaurant et sa fermeture.
     * Il ne peut pas y avoir de nouveaux clients ajoutés à la file d'attente
     * après la dureeOuverture.
     */
    private long dureeOuverture;
    
    /**
     * la cadence d'arrivée des clients en fonction du pourcentage de temps
     * d'ouverture écoulé.
     * <pre> {@code
     * 0 <= alpha <= 1
     * 0 <= tempsSimu = alpha * dureeOuverture <= dureeOuverture
     * tempsMoyenEntreClient(alpha) : duree moyenne d'apparition d'un nouveau
     * client au tempsSimu = alpha * dureeOuverture.
     * } </pre>
     */
    private FonctionLineaireParMorceaux tempsMoyenEntreClient;
    
    /**
     * nombre moyen de plat commandés en du pourcentage de temps
     * d'ouverture écoulé.
     */
    private FonctionLineaireParMorceaux tailleMoyenneCommande;
    
    
}
