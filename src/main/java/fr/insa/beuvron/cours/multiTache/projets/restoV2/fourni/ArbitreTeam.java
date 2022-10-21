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

import java.util.ArrayList;
import java.util.List;

/**
 * Un arbitre qui "dispatch" les évènements à un ensemble d'arbitres.
 * Très pratique pour avoir un arbitre qui ne fait qu'afficher à la console,
 * pendant qu'un autre affiche de façon graphique, et enfin un vrai arbitre
 * qui vérifie que les évènements sont cohérents avec le problème
 * @author francois
 */
public class ArbitreTeam implements Arbitre{
    
    private List<Arbitre> arbitres;

    public ArbitreTeam(List<Arbitre> arbitres) {
        this.arbitres = arbitres;
    }
    
    public ArbitreTeam() {
        this(new ArrayList<>());
    }
    
    public void addArbitre(Arbitre a) {
        this.arbitres.add(a);
    }

    public List<Arbitre> getArbitres() {
        return arbitres;
    }

    @Override
    public void newEvent(Event e) {
        for (Arbitre a : this.arbitres) {
            a.newEvent(e);
        }
    }
    
    
    
}
