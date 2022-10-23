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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pour éviter que l'arbitre travaille sur le même thread, on le fait travailler
 * dans un thread à part.
 * Note : comme souvent dans cette V1, il n'y a rien pour arrêter proprement
 * cet arbitre.
 * <pre> {@code
 *   . les évènements quand ils arrivent sont simplement stockés dans une
 *     blocking queue FIFO.
 *   . on lance un thread qui attend un evenement sur cette queue, et appelle
 *     l'arbitre réel dès qu'il y en a au moins un en attente sur la queue,
 *     on appelle newEvent de l'arbitre réel (mais donc dans le thread de
 *     l'ArbitreDansThreadSepare, et non dans le thread du programme qui
 *     a appelé newEvent)
 * } </pre>
 * @author francois
 */
public class ArbitreDansThreadSepare implements Arbitre{
    
    private Arbitre realArbitre;
    
    private BlockingQueue<Event> eventQueue;

    public ArbitreDansThreadSepare(Arbitre arbitre) {
        this.realArbitre = arbitre;
        this.eventQueue = new LinkedBlockingQueue<>();
        new EventConsumer().start();
    }
    
    public Arbitre getRealArbitre() {
        return realArbitre;
    }
    
    public class EventConsumer extends Thread{
        @Override
        public void run() {
            while(true) {
                try {
                    Event next = eventQueue.take();
                    realArbitre.newEvent(next);
                } catch (InterruptedException ex) {
                    throw new Error("unexpected interrupt : " + ex);
                }
            }
        }
    }

    @Override
    public void newEvent(Event e) {
        this.eventQueue.offer(e);
    }
    
    
    
}
