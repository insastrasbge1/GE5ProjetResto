/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
