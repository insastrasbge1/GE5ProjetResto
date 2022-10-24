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
public class PlusieursEmployesSimuResto extends SimuResto {

    private long dureeServiceToutCompris;

    private int nbrEmployes;

    public PlusieursEmployesSimuResto(long dureeServiceToutCompris, int nbrEmployes) {
        this.dureeServiceToutCompris = dureeServiceToutCompris;
        this.nbrEmployes = nbrEmployes;
    }

    @Override
    public void start() {
        for(int i = 0 ; i < this.nbrEmployes ; i ++) {
            new UnEmploye(i).start();
        }
    }
    

    public class UnEmploye extends Thread {
        
        private int id;

        public UnEmploye(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                Optional<CommandeClient> nextCommande;
                SimulateurGlobal simu = getSimu();
                GestionnaireFileAttente file = simu.getFileAttente();
                while ((nextCommande = file.prendCommande()).isEmpty()) {
                    file.attendsUnClient(Long.MAX_VALUE);
                }
                simu.getArbitre().newEvent(
                        new Event.DebutPriseCommande(simu, nextCommande.orElseThrow(), this.id,
                                1,  // toujours caisse 1
                                simu.getGestionnaireTemps().currentTimeResto()));
                simu.getGestionnaireTemps().sleepDureeResto(
                        (long) (dureeServiceToutCompris* (0.5 + simu.getGlobalRandom().nextDouble())));
                simu.getArbitre().newEvent(
                        new Event.FinPriseCommande(simu, this.id,
                                simu.getGestionnaireTemps().currentTimeResto()));
            }
        }
    }

}
