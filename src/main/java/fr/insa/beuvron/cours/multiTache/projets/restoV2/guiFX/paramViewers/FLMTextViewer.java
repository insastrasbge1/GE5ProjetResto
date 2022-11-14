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
import javafx.scene.control.TextField;

/**
 *
 * @author francois
 */
public class FLMTextViewer extends TextField {

    private FonctionLineaireParMorceaux flm;

    public FLMTextViewer(FonctionLineaireParMorceaux flm) {
        this.flm = flm;

    }

    public final void updateView() {
        this.setText(this.flm.toString());
    }

}
