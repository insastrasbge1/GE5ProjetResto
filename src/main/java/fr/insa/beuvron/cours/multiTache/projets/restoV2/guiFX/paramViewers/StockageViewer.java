/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX.paramViewers;

import fr.insa.beuvron.cours.multiTache.projets.restoV2.parametres.Stockage;
import javafx.scene.control.TextField;

/**
 *
 * @author francois
 */
public class StockageViewer extends TextField{
    
    private Stockage data;

    public StockageViewer(Stockage data) {
        this.data = data;
        this.updateView();
    }

    public final void updateView() {
        this.setText(this.data.toString());
    }
    
    
    
}
