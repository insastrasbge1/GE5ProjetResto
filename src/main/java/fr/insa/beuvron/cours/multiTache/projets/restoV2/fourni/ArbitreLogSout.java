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
 * Un arbitre qui ne fait qu'afficher les évèenement sur la sortie standard.
 *
 * @author francois
 */
public class ArbitreLogSout implements Arbitre {

    /**
     * affiche ou non les évènement de type Debug.
     * <pre> {@code
     * si < 0 ==> aucun message de debug
     * si == 0 ==> seuls les messages les plus généraux
     * ... plus debugLevel est grand, plus on affiche des messages détaillés
     * } </pre>
     */
    private int debugLevel;

    public ArbitreLogSout(int debugLevel) {
        this.debugLevel = debugLevel;
    }

    public int getDebugLevel() {
        return debugLevel;
    }

    public void setDebugLevel(int debugLevel) {
        this.debugLevel = debugLevel;
    }

    @Override
    public void newEvent(Event e) {
        if (e instanceof Event.DebugEvent) {
            Event.DebugEvent ed = (Event.DebugEvent) e;
            if (ed.getDebugLevel() <= debugLevel) {
                System.out.println(ed);
            }
        } else {
            System.out.println(e);
        }
    }

}
