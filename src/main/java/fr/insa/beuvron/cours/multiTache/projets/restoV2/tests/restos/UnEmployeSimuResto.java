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
        SimulateurGlobal simu = this.getSimu();
        while (!simu.getFileAttente().fileClosed()) {
            Optional<CommandeClient> nextCommande = Optional.empty();
            GestionnaireFileAttente file = simu.getFileAttente();
            while (!simu.getFileAttente().fileClosed() && (nextCommande = file.prendCommande()).isEmpty()) {
                file.attendsUnClient(1000);
            }
            if (!nextCommande.isEmpty()) {
                simu.getArbitre().newEvent(
                        new Event.DebutPriseCommande(simu, nextCommande.orElseThrow(),
                                1, // toujours employe 1
                                1, // toujours caisse 1
                                simu.getGestionnaireTemps().currentTimeResto()));
                simu.getGestionnaireTemps().sleepDureeResto(dureeServiceToutCompris);
                simu.getArbitre().newEvent(
                        new Event.FinPriseCommande(simu, 1,
                                simu.getGestionnaireTemps().currentTimeResto()));
            }
        }
    }

}
