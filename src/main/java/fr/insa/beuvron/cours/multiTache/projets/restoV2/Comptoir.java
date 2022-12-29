package fr.insa.beuvron.cours.multiTache.projets.restoV2;

public class Comptoir {

    private Caisse[] lesCaisses;
    private boolean[] caissesDispos;

    public Comptoir(Caisse[] caisses) {
        this.lesCaisses = caisses;
        boolean[] tab = new boolean[caisses.length];
        for(int i=0; i<tab.length; i++){
            tab[i]=true;
        }
        this.caissesDispos = tab;
    }

    public Caisse[] getLesCaisses() {
        return this.lesCaisses;
    }

    public synchronized int reserveCaisse(){
        int i = 0;
        while(!caissesDispos[i] && i < lesCaisses.length){
            if(i < lesCaisses.length - 1){
                i++;
            }
            else{
                break;
            }
        }
        if(i < lesCaisses.length){
            return lesCaisses[i].getId();
        }
        else{
            return -1;
        }
    }

    public synchronized void libereCaisse(Caisse caisseALiberer){
        this.caissesDispos[caisseALiberer.getId() - 1] = true;
    }    
}