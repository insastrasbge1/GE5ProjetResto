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

import fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni.Utils;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.ParametresSimulation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author francois
 */
public class AllParasViewer extends VBox {

    private ParametresSimulation paras;

    private RestaurantViewer restoV;
    private TextField tfDureeOuverture;
    private TextField tfTailleMaxFileAttente;

    public AllParasViewer(ParametresSimulation paras) {
        this.paras = paras;
        
        this.restoV = new RestaurantViewer(this.paras.getResto());
        this.getChildren().add(this.restoV);

        GridPane gpParasSimples = new GridPane();
        int lig = 0;
        gpParasSimples.add(new Label("dureeOuverture"), 0, lig);
        this.tfDureeOuverture = new TextField();
        gpParasSimples.add(this.tfDureeOuverture, 1, lig);
        lig++;
        gpParasSimples.add(new Label("tailleMaxFileAttente"), 0, lig);
        this.tfTailleMaxFileAttente = new TextField();
        gpParasSimples.add(this.tfTailleMaxFileAttente, 1, lig);
        lig++;
        this.getChildren().add(gpParasSimples);

    }

    public final void updateView() {
        this.restoV.updateView();
        this.tfDureeOuverture.setText(Utils.formatDuree(
                this.paras.getDureeOuverture()));
        this.tfTailleMaxFileAttente.setText(
                "" + this.paras.getTailleMaxFileAttente());
    }

}
