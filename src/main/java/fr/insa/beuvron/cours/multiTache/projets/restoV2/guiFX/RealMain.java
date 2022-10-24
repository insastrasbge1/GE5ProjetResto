/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX;

import fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX.paramViewers.RestaurantViewer;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.ParametresSimulation;

/**
 *
 * @author francois
 */
public class RealMain {

    public static void realMain() {
//        FXUtils.showInStage(new HBox(new Label("coucou FX")));
        ParametresSimulation paras = ParametresSimulation.parasTest();
//        FXUtils.showInStage(new TypePlatViewer(paras.getResto().getCarte().getLesPlats()[0]));
//        FXUtils.showInStage(new CarteViewer(paras.getResto().getCarte()));
        FXUtils.showInStage(new RestaurantViewer(paras.getResto()));
        
    }

}
