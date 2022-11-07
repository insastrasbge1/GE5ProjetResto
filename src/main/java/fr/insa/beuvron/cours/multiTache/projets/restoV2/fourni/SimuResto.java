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
