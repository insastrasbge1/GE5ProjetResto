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

import java.util.Optional;

/**
 * Une classe pour faire le lien entre le temps ordi (temps tel que défini
 * par l'horloge interne de l'ordi) et le temps de simulation.
 * On veut "accélérer" le temps : classiquement, un resto reste ouvert quelques
 * heures. Il n'est pas question que le programme de simulation prenne plusieurs
 * heures à s'exécuter. On défini donc un "multiplicateur de temps" mt : chaque
 * seconde de temps ordi représente mt secondes de temps simulation.
 * @author francois
 */
public class GestionnaireTemps {
    
    /**
     * une seconde de temps ordi represente "multiplicateur" secondes de temps 
     * simulation.
     */
    private long multiplicateur;
    
    /**
     * il faut appeler la méthode {@link #debutSimulation() } pour fixer
     * le temps de début.
     */
    private Optional<Long> tempsOrdiDebut;
    
    /**
     * duree de la simulation en temps simulation.
     */
    private long dureeSimulation;

    public GestionnaireTemps(long multiplicateur,long dureeSimulation) {
        this.multiplicateur = multiplicateur;
        this.dureeSimulation = dureeSimulation;
        this.tempsOrdiDebut = Optional.empty();
    }
    
    public void debutSimulation() {
        this.tempsOrdiDebut = Optional.of(System.currentTimeMillis());
    }
    
    public long currentTimeSimulation() {
        long debutOrdi = this.tempsOrdiDebut.orElseThrow();
        long curTimeOrdi = System.currentTimeMillis();
        return (curTimeOrdi - debutOrdi) * this.multiplicateur;
    }
    
    /**
     * calcule de pourcentage de temps écoulé.
     * <pre> {@code 
     * soit curOrdi le temps ordi (System.System.currentTimeMillis())
     * curDureeOrdi = curOrdi - tempsOrdiDebut
     * dureeOrdiTotale = dureeSimulation / multiplicateur
     * percentEcoule = dureeOrdiTotale / curDureeOrdi
     * } </pre>
     * @return 
     */
    public double pourcentEcoule() {
        long curOrdi = System.currentTimeMillis();
        long curDureeOrdi = curOrdi - this.tempsOrdiDebut.orElseThrow();
        double dureeOrdiTotale = ((double) this.dureeSimulation) / this.multiplicateur;
        double percentEcoule = dureeOrdiTotale / curDureeOrdi;
        return percentEcoule;
    }
    
}
