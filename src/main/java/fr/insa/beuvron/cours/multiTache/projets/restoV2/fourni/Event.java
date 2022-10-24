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
package fr.insa.beuvron.cours.multiTache.projets.restoV2.fourni;

/**
 *
 * @author francois
 */
public abstract class Event {
    
    /**
     * il peut paraître un peu lourd d'associer tout Event avec le simulateur
     * qui l'a générer, mais comme cela, on a potentiellement accès à tous les
     * paramètres permettant de le gérer.
     */
    private SimulateurGlobal simu;

    public Event(SimulateurGlobal simu) {
        this.simu = simu;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public SimulateurGlobal getSimu() {
        return simu;
    }

    public static abstract class TempsOrdiEvent extends Event {

        private long tempsOrdi;

        public TempsOrdiEvent(SimulateurGlobal simu) {
            super(simu);
            this.tempsOrdi = System.currentTimeMillis();
        }

        public long getTempsOrdiDebut() {
            return tempsOrdi;
        }

        @Override
        public String toString() {
            // ce devrait être un formatInstant, mais l'écriture est trop lourde
            // on format comme une durée depuis le début de la simu
            long duree = this.tempsOrdi - this.getSimu().getGestionnaireTemps().getTempsOrdiDebut();
            return super.toString() + " [[" + Utils.formatDuree(duree) + "]]";
        }
    }

    public static class DebutSimulation extends TempsOrdiEvent {

        public DebutSimulation(SimulateurGlobal simu) {
            super(simu);
        }
        
    }

    public static class FileAttenteStarted extends TempsOrdiEvent {

        public FileAttenteStarted(SimulateurGlobal simu) {
            super(simu);
        }
        
    }

    public static class FileAttenteEnded extends TempsOrdiEvent {

        public FileAttenteEnded(SimulateurGlobal simu) {
            super(simu);
        }
        
    }

    /**
     * évenements ne faisant pas partie de la simulation. peut être utilisé en
     * phase de débug
     */
    public static class DebugEvent extends TempsOrdiEvent {

        /**
         * Un entier positif ou nul, plus le debug level est grand, plus le
         * message correspond à un détail.
         * <pre> {@code
         * typiquement :
         *   0 : message de débug très génériques (ex : début de la simu)
         *   1 : message un peu plus détaillé (ex : un employé vient de servir un client)
         *   2 : mesage très détaillé : (ex : un employé est en attente sur le lock de file d'attente non vide)
         * } </pre> typiquement :
         */
        private int debugLevel;

        private String debugMessage;

        public DebugEvent(SimulateurGlobal simu,int debugLevel, String debugMessage) {
            super(simu);
            this.debugLevel = debugLevel;
            this.debugMessage = debugMessage;
        }

        @Override
        public String toString() {
            return super.toString() + " (" + debugLevel + ") : " + debugMessage;
        }

        public int getDebugLevel() {
            return debugLevel;
        }

        public String getDebugMessage() {
            return debugMessage;
        }
    }

    public static abstract class RestoEvent extends Event {

        private long tempsResto;

        public RestoEvent(SimulateurGlobal simu,long tempsResto) {
            super(simu);
            this.tempsResto = tempsResto;
        }

        public long getTempsResto() {
            return tempsResto;
        }

        @Override
        public String toString() {
            // ce devrait être un formatInstant, mais l'écriture est trop lourde
            // on format comme une durée depuis le début de la simu
            return super.toString() + " [" + Utils.formatDuree(tempsResto) + "]";
        }

    }

    public static abstract class FileEvent extends RestoEvent {

        private int clientID;

        public FileEvent(SimulateurGlobal simu, int clientID, long tempsResto) {
            super(simu,tempsResto);
            this.clientID = clientID;
        }

        public int getClientID() {
            return clientID;
        }

        public String toString() {
            return super.toString() + " ; clientID = " + clientID;
        }
    }

    public static class ClientArrive extends FileEvent {

        public ClientArrive(SimulateurGlobal simu, int clientID, long tempsResto) {
            super(simu,clientID, tempsResto);
        }

    }

    public static class ClientRepartImmediatement extends FileEvent {

        public ClientRepartImmediatement(SimulateurGlobal simu, int clientID, long tempsResto) {
            super(simu,clientID, tempsResto);
        }
    }

    public static class ClientEntreDansFile extends FileEvent {

        public ClientEntreDansFile(SimulateurGlobal simu, int clientID, long tempsResto) {
            super(simu,clientID, tempsResto);
        }
    }

    public static abstract class EmployeEvent extends RestoEvent {

        private int employeID;

        public EmployeEvent(SimulateurGlobal simu, int employeID, long tempsResto) {
            super(simu,tempsResto);
            this.employeID = employeID;
        }

        public int getEmployeID() {
            return employeID;
        }

        @Override
        public String toString() {
            return super.toString() + " ; employeID = " + this.employeID;
        }
    }

    public static class DebutPriseCommande extends EmployeEvent {

        private CommandeClient commande;
        private int numCaisse;

        public DebutPriseCommande(SimulateurGlobal simu, 
                CommandeClient commande, int employeID, int numCaisse,
                long tempsResto) {
            super(simu,employeID, tempsResto);
            this.numCaisse = numCaisse;
            this.commande = commande;
        }

        public CommandeClient getCommande() {
            return commande;
        }

        @Override
        public String toString() {
            return super.toString() 
                    + " ; commande = " + this.commande
                    + " ; caisse = " + this.numCaisse;
        }
    }

    public static class FinPriseCommande extends EmployeEvent {

        public FinPriseCommande(SimulateurGlobal simu, int employeID, long tempsResto) {
            super(simu,employeID, tempsResto);
        }

    }

}
