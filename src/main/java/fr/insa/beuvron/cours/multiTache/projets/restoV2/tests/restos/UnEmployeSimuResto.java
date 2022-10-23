/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.tests.restos;

import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.CommandeClient;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.Event;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.GestionnaireFileAttente;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.SimuResto;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.SimulateurGlobal;
import java.util.Optional;

/**
 * Un squelette de SimuResto qui ne fait pas grand chose, et ne respecte aucune
 * règle.
 *
 * @author francois
 */
public class UnEmployeSimuResto extends SimuResto {

    private long dureeServiceToutCompris;

    public UnEmployeSimuResto(long dureeServiceToutCompris) {
        this.dureeServiceToutCompris = dureeServiceToutCompris;
    }

    @Override
    public void start() {
        while (true) {
            Optional<CommandeClient> nextCommande;
            SimulateurGlobal simu = this.getSimu();
            GestionnaireFileAttente file = simu.getFileAttente();
            while ((nextCommande = file.prendCommande()).isEmpty()) {
                file.attendsUnClient(Long.MAX_VALUE);
            }
            simu.getArbitre().newEvent(
                    new Event.DebutPriseCommande(simu,nextCommande.orElseThrow(), 1,
                            simu.getGestionnaireTemps().currentTimeResto()));
            simu.getGestionnaireTemps().sleepDureeResto(dureeServiceToutCompris);
            simu.getArbitre().newEvent(
                    new Event.FinPriseCommande(simu,1,
                            simu.getGestionnaireTemps().currentTimeResto()));
        }
    }

}
