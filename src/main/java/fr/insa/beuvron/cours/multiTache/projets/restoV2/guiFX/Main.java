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
package fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * JavaFx nécessite une initialisation au travers d'une classe Application.
 * <pre> {@code 
 * - vous devez lancer le "goal" maven "javafx:run" pour exécuter l'application
 * 
 * - l'utilisation "normale" de javaFX est d'avoir dès le démarrage une fenêtre,
 * fenêtre principale de l'application.
 * Cela se fait au travers d'une classe Application, qui initialise JavaFX et 
 * crée une première fenêtre (Stage JavaFX)
 * Je n'ai pas trouvé comment initialiser correctement JavaFX sans passer par 
 * cette classe Application qui crée une fenêtre initiale (principale)
 * Mais dans mon application de simulateur, je n'ai pas de fenêtre principale  :
 * je veux simplement pouvoir créer et visualiser "à la volée" des fenêtres
 * correspondant à divers objets (par exemple des arbitres graphiques)
 * D'où ce petit subterfuge : je crée une fenêtre invisible, puis le vrai
 * début du programme est dans RealMain.realMain();
 * } </pre>
 * @author francois
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene sc = new Scene(new Label("not used"));
//        Scene sc = new Scene(new TestFx());
        stage.setScene(sc);
        stage.setTitle("Not seen");
        // NO 
//          stage.show();

        // now the real main
        RealMain.realMain();
    }

    public static void main(String[] args) {
        launch();
    }

}
