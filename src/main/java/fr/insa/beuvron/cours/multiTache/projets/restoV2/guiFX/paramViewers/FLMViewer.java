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

import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX.FXUtils;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author francois
 */
public class FLMViewer extends HBox {

    private FonctionLineaireParMorceaux flm;
    private String title;
    private String xAxisTitle;
    private String yAxisTitle;

    private FLMTextViewer flmText;

    public FLMViewer(FonctionLineaireParMorceaux flm,
            String title, String xAxisTitle, String yAxisTitle) {
        this.flm = flm;
        this.title = title;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;

        this.flmText = new FLMTextViewer(flm);
        this.getChildren().add(this.flmText);

        Button showInStage = new Button("voir...");
        showInStage.setOnAction((t) -> {
            FXUtils.showInStage(new FLMChart(flm, this.title, this.xAxisTitle,this.yAxisTitle));
        });
        this.getChildren().add(showInStage);
        this.updateView();
    }

    public final void updateView() {
        this.flmText.updateView();
    }

}
