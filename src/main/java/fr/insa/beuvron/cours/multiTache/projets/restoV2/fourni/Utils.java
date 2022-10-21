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

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author francois
 */
public class Utils {

    public static String formatInstant(long timeStamp) {
        Instant t = Instant.ofEpochMilli(timeStamp);
        return t.toString();
    }

    public static String formatDuree(long dureeInMs) {
        Duration d = Duration.ofMillis(dureeInMs);
        return d.toString().substring(2);
    }

    public static String affDurees(long[] durees) {
        StringBuilder res = new StringBuilder();
        res.append("[");
        for(int i = 0 ; i < durees.length ; i ++) {
            res.append(formatDuree(durees[i]));
            if (i != durees.length-1) {
                res.append(",");
            }
        }
        res.append("]");
        return res.toString();
    }

    public static long minToMs(long minutes) {
        return minutes * 60000;
    }

    public static long hToMs(long heures) {
        return heures * 60 * 60000;
    }

}
