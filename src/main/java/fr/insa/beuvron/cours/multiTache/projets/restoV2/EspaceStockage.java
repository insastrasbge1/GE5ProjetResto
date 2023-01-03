package fr.insa.beuvron.cours.multiTache.projets.restoV2;

public class EspaceStockage
{
    private int[] stocksActuels;

    public EspaceStockage(int[] stocksActuels) {
        this.stocksActuels = stocksActuels;
    }

    public int[] getStocksActuels() {
        return stocksActuels;
    }

    public void setStocksActuels(int[] stocksActuels) {
        this.stocksActuels = stocksActuels;
    }

    public synchronized int reserveStock() {
        try {
            this.wait();
            return 1;
        } catch (InterruptedException e) {
            System.out.println("Espace de stockage non disponible");
            return -1;
        }
    }
    
    public synchronized void libereStock() {
        this.notifyAll();
    }

}