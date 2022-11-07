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
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.Restaurant;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public class RestaurantViewer extends VBox{
    
    private Restaurant data;
    
    private CarteViewer carteViewer;
    private StockageViewer stockageViewer;
    private TextField tfNbrEmployes;
    private TextField tfNbrCaisse;
    private TextField tfDureeCommande;
    private TextField tfDureeChargement;
    private TextField tfDureeDechargement;
    
    public RestaurantViewer(Restaurant data) {
        this.data = data;
        this.carteViewer = new CarteViewer(this.data.getCarte());
        FXUtils.addSimpleBorder(this.carteViewer, Color.GREEN,2);
        ScrollPane spCarte = new ScrollPane(this.carteViewer);
        this.getChildren().add(this.carteViewer);
        this.stockageViewer = new StockageViewer(this.data.getStockage());
        GridPane main = new GridPane();
        int lig = 0;
        main.add(new Label("stockage : "), 0, lig);
        main.add(this.stockageViewer, 1, lig);
        lig++;
        main.add(new Label("NbrEmployes : "), 0, lig);
        this.tfNbrEmployes = new TextField();
        main.add(this.tfNbrEmployes, 1, lig);
        lig++;
        main.add(new Label("NbrCaisse : "), 0, lig);
        this.tfNbrCaisse = new TextField();
        main.add(this.tfNbrCaisse, 1, lig);
        lig++;
        main.add(new Label("DureeCommande : "), 0, lig);
        this.tfDureeCommande = new TextField();
        main.add(this.tfDureeCommande, 1, lig);
        lig++;
        main.add(new Label("DureeChargement : "), 0, lig);
        this.tfDureeChargement = new TextField();
        main.add(this.tfDureeChargement, 1, lig);
        lig++;
        main.add(new Label("DureeDechargement : "), 0, lig);
        this.tfDureeDechargement = new TextField();
        main.add(this.tfDureeDechargement, 1, lig);
        lig++;
        this.getChildren().add(main);
        this.updateView();
        
    }

    public final void updateView() {
        this.carteViewer.updateView();
        this.stockageViewer.updateView();
        this.tfNbrEmployes.setText(""+this.data.getNbrEmployes());
        this.tfNbrCaisse.setText(""+this.data.getNbrCaisse());
        this.tfDureeCommande.setText(""+this.data.getDureeCommande());
        this.tfDureeChargement.setText(""+this.data.getDureeChargement());
        this.tfDureeDechargement.setText(""+this.data.getDureeDechargement());
    }
    
}
