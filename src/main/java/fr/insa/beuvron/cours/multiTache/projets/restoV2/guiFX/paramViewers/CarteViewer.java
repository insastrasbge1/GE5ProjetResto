/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX.paramViewers;

import fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX.FXUtils;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.Carte;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public class CarteViewer extends VBox {
    
    private Carte data;
    
    private GridPane carte;
    
    public CarteViewer(Carte data) {
        this.data = data;
        this.getChildren().add(new Label("Carte"));
        this.carte = new GridPane();
        this.getChildren().add(this.carte);
        this.updateView();
    }

    public final void updateView() {
        this.carte.getChildren().clear();
        for(int col = 0 ; col < this.data.getLesPlats().length ; col ++) {
            TypePlatViewer pv = new TypePlatViewer(this.data.getLesPlats()[col]);
            FXUtils.addSimpleBorder(pv, Color.GRAY, 1);
            this.carte.add(pv, col , 0);
        }
    }
    
}
