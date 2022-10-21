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

import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.Utils;
import fr.insa.beuvron.utils.StringUtil;
import java.util.Arrays;

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
 *
 * @author francois
 */
public class ParametresSimulation {

    private Restaurant resto;
    /**
     * temps (simulation) entre l'ouverture du restaurant et sa fermeture. Il ne
     * peut pas y avoir de nouveaux clients ajoutés à la file d'attente après la
     * dureeOuverture.
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
     * nombre moyen de plat commandés en fonction du pourcentage de temps
     * d'ouverture écoulé.
     */
    private FonctionLineaireParMorceaux tailleMoyenneCommande;

    /**
     * proba de commande d'un plat donné en fonction du pourcentage de temps
     * d'ouverture écoulé. Une "proba" pour chaque type de plat dans la carte
     */
    private FonctionLineaireParMorceaux[] probasRelativePlats;

    /**
     * nombre maximal de clients qui attendent dans la file d'attente
     */
    private int tailleMaxFileAttente;

    /**
     * probabilité qu'un client qui arrive dans la file d'attente reste
     * effectivement, en fonction du remplissage de la file d'attente.
     * <pre> {@code
     *   soit
     *     . nbrInFile : nombre actuel d'employés dans la file d'attente
     *     . percent = nbrInFile / tailleMaxFileAttente
     *     . probaClientReste(percent) donne la proba que le client reste
     *
     *   Note : si nbrInFile == tailleMaxFileAttente
     *    la proba est 0 (le client ne reste pas) quelque soit la valeur
     *    de probaClientReste(1)
     * } </pre> proba(percentRemplissage
     */
    private FonctionLineaireParMorceaux probaClientReste;

    public ParametresSimulation(Restaurant resto,
            long dureeOuverture,
            FonctionLineaireParMorceaux tempsMoyenEntreClient,
            FonctionLineaireParMorceaux tailleMoyenneCommande,
            FonctionLineaireParMorceaux[] probasRelativePlats,
            int tailleMaxFileAttente,
            FonctionLineaireParMorceaux probaClientReste) {
        if (resto.getCarte().getLesPlats().length !=
                probasRelativePlats.length) {
            throw new Error("il faut des probas relatives pour chaque type de plat dans la carte");
        }
        this.resto = resto;
        this.dureeOuverture = dureeOuverture;
        this.tempsMoyenEntreClient = tempsMoyenEntreClient;
        this.tailleMoyenneCommande = tailleMoyenneCommande;
        this.probasRelativePlats = probasRelativePlats;
        this.tailleMaxFileAttente = tailleMaxFileAttente;
        this.probaClientReste = probaClientReste;
    }
    
    public static String formatFLMArray(FonctionLineaireParMorceaux[] fs) {
        StringBuilder res = new StringBuilder();
        res.append("{\n");
        for(var f : fs) {
            res.append("  ");
            res.append(f);
        }
        res.append("\n}");
        return res.toString();
    }

    @Override
    public String toString() {
        return "ParametresSimulation{"
                + "\n" + StringUtil.specialIndent(resto.toString()," | ")
                + "\n | dureeOuverture=" + dureeOuverture
                + "\n | tempsMoyenEntreClient=" + tempsMoyenEntreClient
                + "\n | tailleMoyenneCommande=" + tailleMoyenneCommande
                + "\n | probasRelativePlats=" + Arrays.toString(probasRelativePlats)
                + "\n | tailleMaxFileAttente=" + tailleMaxFileAttente
                + "\n | probaClientReste=" + probaClientReste
                + "\n}";
    }

    public static ParametresSimulation parasTest() {
        Restaurant resto = Restaurant.restoTest();
        FonctionLineaireParMorceaux tempsMoyenEntreClient
                = new FonctionLineaireParMorceaux(new double[][]{
            {0, Utils.minToMs(10)},
            {0.5, Utils.minToMs(1)},
            {0.75, Utils.minToMs(1)},
            {1, Utils.minToMs(10)},});
        FonctionLineaireParMorceaux tailleMoyenneCommande
                = new FonctionLineaireParMorceaux(new double[][]{
            {0, 2},
            {0.5, 7},
            {0.75, 10},
            {1, 2},});
        FonctionLineaireParMorceaux[] probasRelativePlats
                = new FonctionLineaireParMorceaux[]{
                    // burger
                    new FonctionLineaireParMorceaux(new double[][]{
                {0, 50},
                {0.5, 50 },
                {0.75, 50},
                {1, 50},}),
                    // frites
                    new FonctionLineaireParMorceaux(new double[][]{
                {0, 20},
                {0.5, 50},
                {0.75, 70},
                {1, 50},}),
                    // salade
                    new FonctionLineaireParMorceaux(new double[][]{
                {0, 100},
                {0.5, 50},
                {0.75, 20},
                {1, 10},}),};
        int tailleMaxFileAttente = 10;
        FonctionLineaireParMorceaux probaClientReste
                = new FonctionLineaireParMorceaux(new double[][]{
            {0, 1.0},
            {0.25, 0.9},
            {0.75, 0.25},
            {1, 0.0},}); 
        return new ParametresSimulation(resto, Utils.hToMs(4),
                tempsMoyenEntreClient, 
                tailleMoyenneCommande, probasRelativePlats, 
                tailleMaxFileAttente, probaClientReste);
    }
    
    public static void main(String[] args) {
        System.out.println("paramètres de simulation pour les tests :\n" +
                parasTest());
    }

}
