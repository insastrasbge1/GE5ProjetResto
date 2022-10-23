/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni;

/**
 * Cette classe doit être spécialisée pour simuler effectivement le fonctionnement du resto.
 * Elle garde un lien vers le simulateur global pour avoir accès en particulier
 * à la file d'attente au gestionnaire de temps et à l'arbitre.
 * Petit problème technique : le SimulateurGlobal a besoin de connaître le SimuResto,
 * et le SimuResto a besoin de connaitre le SimulateurGlobal.
 * On a un problème de dépendance récursive dans les constructeurs.
 * Le constructeur de SimuResto n'a donc pas de paramètre SimulateurGlobal, mais
 * il faut que le constructeur de SimulateurGlobal appelle setSimu(this).
 * @author francois
 */
public abstract class SimuResto {
    
    /**
     * c'est la méthode qui sera appelé par le simulateur global pour lancer
     * la simulation du resto
     */
    public abstract void start();
    
    private SimulateurGlobal simu;

    public SimuResto() {
        
    }

    public SimulateurGlobal getSimu() {
        return simu;
    }

    public void setSimu(SimulateurGlobal simu) {
        this.simu = simu;
    }
    
}
