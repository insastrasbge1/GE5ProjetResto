package fr.insa.beuvron.cours.multiTache.projets.restoV2;

import javax.print.DocFlavor.STRING;

public class Employe {

    private String nom;
    private int Id;
    private boolean disponible, occupee;

    public Employe(String nom, int Id) {
        this.nom = nom;
        this.Id = Id;
    }

    public String getnom() {
        return this.nom;
    }

    public int getId() {
        return this.Id;
    }

    public void SetBusy(boolean occupee) {
        this.occupee = true;
    }

    public boolean isBusy() {
        return this.occupee;
    }

    public void SetFree(boolean disponible) {
        this.disponible = true;
    }

    public boolean isFree() {
        return this.disponible;
    }

    public static void main(String[] args) {

    }

}