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
package fr.insa.beuvron.cours.multiTache.projets.restoV2.tests;

import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.ArbitreDansThreadSepare;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.ArbitreLogSout;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.SimuResto;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.SimulateurGlobal;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.Utils;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.ParametresSimulation;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.tests.restos.PlusieursEmployesSimuResto;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.tests.restos.UnEmployeSimuResto;
import java.util.Random;

/**
 *
 * @author francois
 */
public class MainText {

    public static void miniTest1() {
        SimuResto resto = new PlusieursEmployesSimuResto(Utils.minToMs(5), 2);
        ParametresSimulation paras = ParametresSimulation.parasMiniTest();
        SimulateurGlobal simu = new SimulateurGlobal(paras,
                resto,
                new ArbitreLogSout(-1),
                500, new Random(123456));
        simu.start();

    }

    public static void test1() {
        SimuResto resto = new PlusieursEmployesSimuResto(Utils.minToMs(20), 50);
//        SimuResto resto = new UnEmployeSimuResto(Utils.minToMs(10));
//        SimuResto resto = new DoNothingSimuResto();
        ParametresSimulation paras = ParametresSimulation.parasTest();
        SimulateurGlobal simu = new SimulateurGlobal(paras,
                resto,
                new ArbitreLogSout(-1),
                2000, new Random(123456));
        simu.start();
    }

    public static void main(String[] args) {
        test1();
//        miniTest1();
    }

}
