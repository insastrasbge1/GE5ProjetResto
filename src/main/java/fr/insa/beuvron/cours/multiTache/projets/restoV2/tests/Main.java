/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class Main {

    public static void test1() {
        SimuResto resto = new PlusieursEmployesSimuResto(Utils.minToMs(100),10);
//        SimuResto resto = new UnEmployeSimuResto(Utils.minToMs(10));
//        SimuResto resto = new DoNothingSimuResto();
        ParametresSimulation paras = ParametresSimulation.parasTest();
        SimulateurGlobal simu = new SimulateurGlobal(paras,
                resto,
                new ArbitreDansThreadSepare(new ArbitreLogSout(-1)),
                1000, new Random(123456));
        simu.start();
    }

    public static void main(String[] args) {
        test1();
    }

}
