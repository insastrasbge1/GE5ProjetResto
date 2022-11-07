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

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author francois
 */
public class FXUtils {

    public static void showInStage(Parent compo, String titre, double width, double heigth) {
        Scene sc = new Scene(compo);
        Stage st = new Stage();
        st.setScene(sc);
        st.setTitle(titre);
        st.setWidth(width);
        st.setHeight(heigth);
        st.show();
    }

    public static void showInStage(Node compo, String titre) {
        Scene sc = new Scene(new BorderPane(compo));
        Stage st = new Stage();
        st.setScene(sc);
        st.setTitle(titre);
        st.show();
    }

    public static void showInStage(Parent compo) {
        showInStage(compo, "Display " + compo.getClass().getSimpleName());
    }

    public static void addSimpleBorder(Region c) {
        addSimpleBorder(c, Color.BLACK);
    }

    public static void addSimpleBorder(Region c,Color color) {
        addSimpleBorder(c, color, 1);
    }

    public static void addSimpleBorder(Region c,double epaisseur) {
        addSimpleBorder(c, Color.BLACK, epaisseur);
    }

    public static void addSimpleBorder(Region c,Color color,double epaisseur) {
        c.setBorder(new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(epaisseur))));
    }

}
