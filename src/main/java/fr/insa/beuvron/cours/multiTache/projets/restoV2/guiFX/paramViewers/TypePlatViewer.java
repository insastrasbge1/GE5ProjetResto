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
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.TypePlat;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author francois
 */
public class TypePlatViewer extends GridPane{
    
    private TypePlat data;
    
    private TextField tfNom;
    private TextField tfDurees;
    private TextField tfCout;
    private TextField tfPrix;
    private TextField tfConservation;
    
    
    public TypePlatViewer(TypePlat data) {
        this.data = data;
//        this.setDisable(true);
        int lig = 0;
        this.add(new Label("nom : "), 0, lig);
        this.tfNom = new TextField();
        this.add(this.tfNom, 1, lig);
        lig ++;
        this.add(new Label("durées : "), 0, lig);
        this.tfDurees = new TextField();
        this.add(this.tfDurees, 1, lig);
        lig ++;
        this.add(new Label("cout : "), 0, lig);
        this.tfCout = new TextField();
        this.add(this.tfCout, 1, lig);
        lig ++;
        this.add(new Label("prix : "), 0, lig);
        this.tfPrix = new TextField();
        this.add(this.tfPrix, 1, lig);
        lig ++;
        this.add(new Label("conservation : "), 0, lig);
        this.tfConservation = new TextField();
        this.add(this.tfConservation, 1, lig);
        lig ++;
        this.refresh();
    }

    public final void refresh() {
        this.tfNom.setText(this.data.getNom());
        this.tfDurees.setText(Utils.affDurees(this.data.getDureesPreparation()));
        this.tfCout.setText("" + this.data.getCoutPreparation());
        this.tfPrix.setText("" + this.data.getPrixVente());
        this.tfConservation.setText("" + this.data.getTempsMaxConservation());
    }
    
}
