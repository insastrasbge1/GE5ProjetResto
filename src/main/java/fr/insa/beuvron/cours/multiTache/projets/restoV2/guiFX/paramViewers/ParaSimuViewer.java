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
import fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX.FXUtils;
import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.ParametresSimulation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public class ParaSimuViewer extends HBox {

    private ParametresSimulation paras;

    private TextField tfDureeOuverture;
    private TextField tfTailleMaxFileAttente;
    
    private FLMViewer flmTempsMoyenEntreClient;
    private FLMViewer flmTailleMoyenneCommande;
    private FLMViewer flmProbaClientReste;
    private ProbaPlatsViewer probasPlatsV;

    public ParaSimuViewer(ParametresSimulation paras) {
        this.paras = paras;
        
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
        
        GridPane gpFLM = new GridPane();
        lig = 0;
        gpFLM.add(new Label("TempsMoyenEntreClient"), 0, lig);
        this.flmTempsMoyenEntreClient = new FLMViewer(paras.getTempsMoyenEntreClient(),
        "TempsMoyenEntreClient","% temps d'ouverture","t en ms");
        gpFLM.add(this.flmTempsMoyenEntreClient, 1, lig);
        lig = lig+1;
        gpFLM.add(new Label("tailleMoyenneCommande"), 0, lig);
        this.flmTailleMoyenneCommande = new FLMViewer(paras.getTailleMoyenneCommande(),
        "TailleMoyenneCommande","% temps d'ouverture","nombre moyen de plats");
        gpFLM.add(this.flmTailleMoyenneCommande, 1, lig);
        lig = lig+1;
        gpFLM.add(new Label("probaClientReste"), 0, lig);
        this.flmProbaClientReste = new FLMViewer(paras.getProbaClientReste(),
        "probaClientReste","nbr de client / taille max file","proba reste");
        gpFLM.add(this.flmProbaClientReste, 1, lig);
        lig = lig+1;
        gpFLM.add(new Label("probas plats"), 0, lig);
        this.probasPlatsV = new ProbaPlatsViewer(paras);
        gpFLM.add(this.probasPlatsV, 1, lig);

        this.getChildren().add(gpFLM);
         this.updateView();
    }

    public final void updateView() {
        this.tfDureeOuverture.setText(Utils.formatDuree(
                this.paras.getDureeOuverture()));
        this.tfTailleMaxFileAttente.setText(
                "" + this.paras.getTailleMaxFileAttente());
        this.flmTempsMoyenEntreClient.updateView();
        this.flmTailleMoyenneCommande.updateView();
    }

}
