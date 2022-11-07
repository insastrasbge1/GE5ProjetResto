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

/**
 * La carte du restaurant : la liste des sandwich proposés
 * @author francois
 */
public class Carte  {
    
    private TypePlat[] lesPlats;
    
    public Carte(TypePlat[] lesPlats) {
        this.lesPlats = lesPlats;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Carte{\n");
        for(var ts : this.lesPlats) {
            res.append(" | ");
            res.append(ts);
            res.append("\n");
        }
        res.append("}");
        return res.toString();
    }
    
    
    
    /**
     * carte :
     * {@code
     * . un seul type de sandwich :
     *   . "Burger"
     *   . aucun avantage à un produire plus de 1 :
     *      . chaque production d'un sandwich prend 1 mn = 60000 ms
     *   . coute 1 euro = 100 centimes à produire
     *   . est vendu 2 euro
     *   . peut être conservé maximum 10mn = 600000 ms
     * }
     * @return 
     */
    public static Carte carteMiniTest() {
        Carte res = new Carte(new TypePlat[] {
            new TypePlat("Burger", new long[] {60000,100000}, 100, 200, 600000)
        });
        return res;
    }
    
    /**
     * carte :
     * {@code
     * . un seul type de sandwich :
     *   . "Burger"
     *   . aucun avantage à un produire plus de 1 :
     *      . chaque production d'un sandwich prend 1 mn = 60000 ms
     *   . coute 1 euro = 100 centimes à produire
     *   . est vendu 2 euro
     *   . peut être conservé maximum 10mn = 600000 ms
     * }
     * @return 
     */
    public static Carte carteTest() {
        Carte res = new Carte(new TypePlat[] {
            TypePlat.burger(),
            TypePlat.frites(),
            TypePlat.salade()
        });
        return res;
    }
    
    public static void main(String[] args) {
        System.out.println(carteTest());
    }
    
 
    /**
     * @return the lesPlats
     */
    public TypePlat[] getLesPlats() {
        return lesPlats;
    }
}
