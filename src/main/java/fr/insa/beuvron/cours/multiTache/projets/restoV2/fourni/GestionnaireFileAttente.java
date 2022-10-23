/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni;

import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;
import fr.insa.beuvron.utils.probas.CalculsDirectDistributions;
import fr.insa.beuvron.utils.probas.TiragesAlea;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public class GestionnaireFileAttente {

    public void start() {
        ClientGenerator gene = this.new ClientGenerator();
        gene.start();
    }

    /**
     * prend la commande du prochain client dans la file d'attente.
     * <pre> {@code
     *  si la file contient au moins un client :
     *    . retire le client de la file
     *    . calcule la commande du client
     *    . retourne Optional.of(new CommandeClient(idClient, commande))
     * sinon
     *    . retourne Optional.empty()
     * } </pre>
     *
     * @return
     */
    public Optional<CommandeClient> prendCommande() {
        this.lockFileAttente.writeLock().lock();
        try {
            if (this.file.isEmpty()) {
                simu.getArbitre().newEvent(new Event.DebugEvent(simu,2, "prendCommande : no client"));
                return Optional.empty();
            } else {
                int idClient = this.file.pollFirst();
                int[] commande = this.calculeCommande();
                CommandeClient res = new CommandeClient(idClient, commande);
                simu.getArbitre().newEvent(new Event.DebugEvent(simu,2, "prendCommande : ok : " + res));
                return Optional.of(res);
            }
        } finally {
            this.lockFileAttente.writeLock().unlock();
        }
    }

    public int nbrCLientsDansFile() {
        this.lockFileAttente.readLock().lock();
        try {
            return this.file.size();
        } finally {
            this.lockFileAttente.readLock().unlock();
        }
    }

    public void attendsUnClient(long maxTimeResto) {
        long maxTimeOrdi = simu.getGestionnaireTemps().dureeRestoVersDureeOrdi(maxTimeResto);
        this.lockFileAttente.writeLock().lock();
        try {
            if (this.file.isEmpty()) {
                try {
                    simu.getArbitre().newEvent(new Event.DebugEvent(simu,2, "waiting for client"));
                    this.fileNonVide.await(maxTimeOrdi, TimeUnit.MILLISECONDS);
                    simu.getArbitre().newEvent(new Event.DebugEvent(simu,2, "wake up ! one client"));
                } catch (InterruptedException ex) {
                    throw new Error("unexpected interrupt of wait : ", ex);
                }
            }
        } finally {
            this.lockFileAttente.writeLock().unlock();
        }
    }

    /**
     * on garde un lien vers le simulateur global, en particulier pour accéder
     * aux paramètres de la simulation et au gestionnaire de temps.
     */
    private SimulateurGlobal simu;

    private ReadWriteLock lockFileAttente;
    private Condition fileNonVide;

    /**
     * la file d'attente effective.
     */
    private LinkedList<Integer> file;

    /**
     * fourni l'identificateur des clients. L'accès doit se faire après
     * aquisition du lock.
     */
    private int nextClientId;

    private int newClient() {
        return this.nextClientId++;
    }

    CalculsDirectDistributions calDistribution;

    /**
     * un petit raccourci.
     *
     * @return
     */
    private int tailleMax() {
        return this.simu.getParasSimu().getTailleMaxFileAttente();
    }

    public GestionnaireFileAttente(SimulateurGlobal simu) {
        this.simu = simu;
        this.lockFileAttente = new ReentrantReadWriteLock(true);
        this.fileNonVide = this.lockFileAttente.writeLock().newCondition();
        this.file = new LinkedList<>();
        this.nextClientId = 1;
        this.calDistribution = new CalculsDirectDistributions(simu.getGlobalRandom());
    }

    private class ClientGenerator extends Thread {

        public double curTempMoyen() {
            double percentOuverture = simu.getGestionnaireTemps().pourcentEcoule();
            simu.getArbitre().newEvent(new Event.DebugEvent(simu,3, " pourcentEcoule : " + percentOuverture));
            FonctionLineaireParMorceaux tempsMoyens = simu.getParasSimu().getTempsMoyenEntreClient();
            return tempsMoyens.valeurEn(percentOuverture);
        }

        @Override
        public void run() {
            simu.newEvent(new Event.FileAttenteStarted(simu));
            double curTM = curTempMoyen();
            simu.getArbitre().newEvent(new Event.DebugEvent(simu,3, " temps moyen : " + curTM));
            long nextClient = (long) calDistribution.loiExponentielle(1 / curTM);
            while (simu.getGestionnaireTemps().currentTimeResto() + nextClient
                    < simu.getParasSimu().getDureeOuverture()) {
                simu.getArbitre().newEvent(new Event.DebugEvent(simu,2, "ClientGenerator : waiting " + Utils.formatDuree(nextClient)));
                simu.getGestionnaireTemps().sleepDureeResto(nextClient);
                clientArrive();
                curTM = curTempMoyen();
                simu.getArbitre().newEvent(new Event.DebugEvent(simu,3, " temps moyen : " + curTM));
                nextClient = (long) calDistribution.loiExponentielle(1 / curTM);
            }
            simu.newEvent(new Event.FileAttenteEnded(simu));
        }
    }

    private void clientArrive() {
        this.lockFileAttente.writeLock().lock();
        try {
            simu.getArbitre().newEvent(new Event.DebugEvent(simu,3, " client arrive ; taille file : " + this.file.size()));
            long time = this.simu.getGestionnaireTemps().currentTimeResto();
            int id = this.newClient();
            this.simu.getArbitre().newEvent(new Event.ClientArrive(simu,id, time));

            if (this.file.size() >= this.tailleMax()) {
                // la file est pleine ==> le client ne reste pas
                this.simu.getArbitre().newEvent(new Event.ClientRepartImmediatement(simu,id, time));
            } else {
                double probaReste = simu.getParasSimu().getProbaClientReste()
                        .valeurEn(((double) this.file.size()) / this.tailleMax());
                simu.getArbitre().newEvent(new Event.DebugEvent(simu,2, " proba reste : " + probaReste));
                if (simu.getGlobalRandom().nextDouble() > probaReste) {
                    this.simu.getArbitre().newEvent(new Event.ClientRepartImmediatement(simu,id, time));
                } else {
                    this.simu.getArbitre().newEvent(new Event.ClientEntreDansFile(simu,id, time));
                    this.file.offer(id);
                    this.fileNonVide.signalAll();
                }
            }
        } finally {
            this.lockFileAttente.writeLock().unlock();
        }
    }

    private int[] calculeCommande() {
        double relTime = simu.getGestionnaireTemps().pourcentEcoule();
        FonctionLineaireParMorceaux fnbr = simu.getParasSimu().getTailleMoyenneCommande();
        double pnbrTot = fnbr.valeurEn(relTime);
        int nbrTot = (int) Math.round(simu.getGlobalRandom().nextDouble()*pnbrTot);
        if (nbrTot == 0) {
            nbrTot = 1;
        }
        int nbrPlat = simu.getParasSimu().getResto().getCarte().getLesPlats().length;
        List<Integer> vals = new ArrayList<>(nbrPlat);
        List<Double> poids = new ArrayList<>(nbrPlat);
        for (int i = 0; i < nbrPlat; i++) {
            vals.add(i);
            poids.add(simu.getParasSimu().getProbasRelativePlats()[i].valeurEn(relTime));
        }
        int[] res = new int[nbrPlat];
        for (int plat = 0; plat < nbrTot; plat++) {
            res[TiragesAlea.choixAleaPondere(TiragesAlea.pondere(vals, poids),
                    simu.getGlobalRandom())]++;
        }
        return res;
    }

}
