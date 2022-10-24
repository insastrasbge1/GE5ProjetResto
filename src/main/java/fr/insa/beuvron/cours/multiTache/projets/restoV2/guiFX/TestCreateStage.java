/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author francois
 */
public class TestCreateStage {

    public static void test() {
        // test appel direct de launch sur Application
//        Application.launch(Main.class);
        Scene sc = new Scene(new Label("la vrai fenetre"));
        Stage st = new Stage();
        st.setScene(sc);
        st.setTitle("coucou");
        st.setWidth(200);
        st.setHeight(200);
        st.showAndWait();

    }

    public static void main(String[] args) {
        test();
    }

}
