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
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.ParametresSimulation;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author francois
 */
public class ProbaPlatsViewer extends HBox {

    private ParametresSimulation paras;

    public ProbaPlatsViewer(ParametresSimulation paras) {
        this.paras = paras;

        GridPane parPlat = new GridPane();
        FonctionLineaireParMorceaux[] probas = this.paras.getProbasRelativePlats();
        for (int lig = 0; lig < probas.length; lig++) {
            String nomPlat = this.paras.getResto().getCarte().getLesPlats()[lig].getNom();
            parPlat.add(new Label(nomPlat), 0, lig);
            FLMViewer fv = new FLMViewer(probas[lig],
                    nomPlat, "% temps d'ouverture", "proba relative");
            parPlat.add(fv, 1, lig);
        }
        this.getChildren().add(parPlat);
    }

}
