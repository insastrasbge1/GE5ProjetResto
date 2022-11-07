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

import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.ParametresSimulation;
import java.util.Random;

/**
 * classe principale de simulation.
 * @author francois
 */
public class SimulateurGlobal {
    
    private ParametresSimulation parasSimu;
    
    private SimuResto gestionnaireResto;
    
    private GestionnaireTemps gestionnaireTemps;
    
    /**
     * note : si vous voulez plusieurs arbitres, il suffit de créer un
     * {@link ArbitreTeam}
     */
    private Arbitre arbitre;
    
    private GestionnaireFileAttente fileAttente;
    
    /**
     * utilisation d'un générateur global pour pouvoir reproduire une séquence.
     * <pre> {@code 
     *   . les méthodes de la classe Random sont threadsafe (voir java api)
     *   . cela peut ralentir l'exécution si de très nombreux threads accèdent
     *     au même random, mais ce n'est normalement pas le cas ici.
     *   . il ne peut pas y avoir une garantie de même séquence dans
     *     un cadre de multi-threading car l'ordre d'exécution des threads
     *     n'est pas déterministe.
     *     ~ on peut tout de même espérer l'avoir, sauf cas rares
     * } </pre>
     */
    private Random globalRandom;

    public SimulateurGlobal(ParametresSimulation parasSimu,
            SimuResto gestionnaireResto,
            Arbitre arbitre,long multiplicateur,Random random) {
        this.globalRandom = random;
        this.parasSimu = parasSimu;
        this.gestionnaireResto = gestionnaireResto;
        this.gestionnaireResto.setSimu(this);
        this.arbitre = arbitre;
        this.gestionnaireTemps = new GestionnaireTemps(multiplicateur,
                this.parasSimu.getDureeOuverture());
        this.fileAttente = new GestionnaireFileAttente(this);
    }
    
    public void start() {
        this.gestionnaireTemps.start();
        this.fileAttente.start();
        this.gestionnaireResto.start();
    }
    
    /**
     * très utilisé, donc un petit raccourci.
     * @param e 
     */
    public void newEvent(Event e) {
        this.arbitre.newEvent(e);
    }

    /**
     * @return the parasSimu
     */
    public ParametresSimulation getParasSimu() {
        return parasSimu;
    }

    public SimuResto getGestionnaireResto() {
        return gestionnaireResto;
    }

    /**
     * @return the gestionnaireTemps
     */
    public GestionnaireTemps getGestionnaireTemps() {
        return gestionnaireTemps;
    }

    /**
     * @return the arbitre
     */
    public Arbitre getArbitre() {
        return arbitre;
    }

    /**
     * @return the fileAttente
     */
    public GestionnaireFileAttente getFileAttente() {
        return fileAttente;
    }

    public Random getGlobalRandom() {
        return globalRandom;
    }
    
    
    
}
