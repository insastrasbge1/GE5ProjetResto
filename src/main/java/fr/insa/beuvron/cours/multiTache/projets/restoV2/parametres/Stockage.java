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

import java.util.Arrays;

/**
 * Les capacités de stockage des divers plats.
 * Attention : le tableau contenant ces capacités doit avoir la même taille
 * que le tableau des plats dans la carte.
 * @author francois
 */
public class Stockage {
    
    private int[] capacites;

    public Stockage(int[] capacités) {
        this.capacites = capacités;
    }

    public int[] getCapacites() {
        return capacites;
    }

    @Override
    public String toString() {
        return "Stockage{" + "capacites=" + Arrays.toString(capacites) + '}';
    }
    
    public static Stockage stockageTest() {
        return new Stockage(new int[] {20,20,30});
    }
    
    
}
