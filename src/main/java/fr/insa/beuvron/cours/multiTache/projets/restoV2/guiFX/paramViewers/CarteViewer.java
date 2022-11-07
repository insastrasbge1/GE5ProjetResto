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
