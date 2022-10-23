/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
