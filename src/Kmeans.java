import java.util.*;

import static java.lang.Integer.parseInt;

public class Kmeans {


    private double[][] distances; // Matrice de distance précalculée
    private int[] clusters; // Tableau de listes pour stocker les points dans chaque cluster

    private int nbclusters; // Nombre de clusters

    private List<Centre> centres; // Liste de centres

    private List<Mission> missions; // Liste de missions

    private List<List<Mission>> listesMissionsCluster;

    private List<List<Employé>> employésParCentres;

    private List<Employé> employes;

    private List<List<Object>> employesMissionsCommunes = new ArrayList<>();




    public Kmeans(double[][] distances, List<Employé> employes, List<Centre> centres, List<Mission> missions, int nbclusters) {
        this.distances = distances;
        this.centres = centres;
        this.nbclusters = nbclusters;
        this.missions = missions;
        this.employes=employes;
        this.listesMissionsCluster = new ArrayList<>();
        this.employésParCentres = new ArrayList<>();
    }

    public List<List<Mission>> getListesMissionsCluster() {
        return listesMissionsCluster;
    }

    public void setListesMissionsCluster(List<List<Mission>> listesMissionsCluster) {
        this.listesMissionsCluster = listesMissionsCluster;
    }

    public double[][] getDistances() {
        return distances;
    }

    public void setDistances(double[][] distances) {
        this.distances = distances;
    }

    public int[] getClusters() {
        return clusters;
    }

    public void setClusters(int[] clusters) {
        this.clusters = clusters;
    }

    public List<Centre> getCentres() {
        return centres;
    }

    public void setCentres(List<Centre> centres) {
        this.centres = centres;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public String toStringKmeans() {
        printMatrix(this.distances);
        return "Kmeans{" +
                "nbcluster=" + this.nbclusters +
                ", centres liste='" + this.centres +
                '}';
    }

    public void printMatrix(double[][] distanceMatrix){
        int numRows = distanceMatrix.length;
        int numCols = distanceMatrix[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(distanceMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void kmeansAlgorithme(){
        int[][] CentreMission = new int[this.distances.length - this.nbclusters][this.nbclusters];

        // on commence à nbcluster car on ne veut pas distances des centres entre eux
        for (int i = this.nbclusters; i < this.distances.length; i++) {
            double minimum = this.distances[i][0];
            int minCol = 0;

            //calcul la distance minimal entre les centres et la mission selectionnée
            for (int j = 1; j < this.nbclusters; j++) {
                if (this.distances[i][j] < minimum) {
                    minimum = this.distances[i][j];
                    minCol = j;
                }
            }

//            System.out.println("Le minimum de la ligne " + (i + 1) + " est : " + minimum + " dans la colonne " + (minCol + 1));

            //TODO
            CentreMission[i-this.nbclusters][0] = i-this.nbclusters+1;
            CentreMission[i-this.nbclusters][1] = minCol+1;
        }


//        System.out.println("\n************************************************************************");
//        System.out.println("Affichage Tableau Mission -> Centre en fonction de la distance la plus courte");
//        System.out.println("************************************************************************");
////        System.out.println("Tableau Centre Missions:");
//        for (int i = 0; i < CentreMission.length; i++) {
//            System.out.println(CentreMission[i][0] + "\t" + CentreMission[i][1]);
//        }


        for (int i = 0; i < this.nbclusters; i++) {
            List<Mission> listeObjets = new ArrayList<>();
            for (int j = 0; j < CentreMission.length; j++) {
                if (CentreMission[j][1] == i+1) {
//                    System.out.println("mission: " +CentreMission[j][0] + "\t Centre: " + CentreMission[j][1]);
                    // Ajoutez les objets souhaités à chaque liste
                    listeObjets.add(missions.get(CentreMission[j][0] -1));
                    //System.out.println(missions.get(CentreMission[j][0] -1).getId());
                }
            }
            listesMissionsCluster.add(listeObjets);
        }

//        System.out.println("\n************************************************************************");
//        System.out.println("Affichage des Clusters");
//        System.out.println("************************************************************************");
//
//        for (int i = 0; i < listesMissionsCluster.size(); i++) {
//            List<Mission> listeObjets = listesMissionsCluster.get(i);
//            System.out.println("Cluster " + (i + 1) + ":");
//            for (Mission objet : listeObjets) {
//                System.out.println("Mission n°" + objet.getId());
//            }
//            System.out.println();
//        }

    }


    public void repartitionEmployéCentre() {
        for (Employé employe : employes) {
            boolean isNewCentre = true;
            for (List<Employé> centre : employésParCentres) {
                if (!centre.isEmpty() && centre.get(0).getCentreID() == employe.getCentreID()) {
                    centre.add(employe);
                    isNewCentre = false;
                    break;
                }
            }
            if (isNewCentre) {
                List<Employé> nouveauCentre = new ArrayList<>();
                nouveauCentre.add(employe);
                employésParCentres.add(nouveauCentre);
            }
        }

        // Affichage des employés par centres
//        System.out.println("\n************************************************************************");
//        System.out.println("Repartition employés par centres");
//        System.out.println("************************************************************************");
//
//        for (int i = 0; i < employésParCentres.size(); i++) {
//            List<Employé> centre = employésParCentres.get(i);
//            System.out.println("Centre " + (i + 1) + ":");
//            for (Employé employe : centre) {
//                System.out.println("Employé n°" + employe.getId() + ", ID Centre : " + employe.getCentreID());
//            }
//            System.out.println();
//        }

    }

    public List<List<List<Mission>>> CompatibiliteMissionEmploye() {
        List<List<List<Mission>>> listeCompatibilite = new ArrayList<>();

        for (int i = 0; i < employésParCentres.size(); i++) {
            List<Employé> centre = employésParCentres.get(i);
            for (Employé employe : centre) {
                List<List<Mission>> missionEmployeJour = new ArrayList<>();
                for (int j = 0; j < 5; j++) {
                    List<Mission> missionCompatibleEmploye = new ArrayList<>();
                    for (Mission mission : listesMissionsCluster.get(i)) {
                        if (Integer.parseInt(mission.getJour()) == (j + 1)) {
                            if (employe.getCompétence().equals(mission.getCompétence())) {
                                missionCompatibleEmploye.add(mission);
                            }
                        }
                    }
                    missionEmployeJour.add(missionCompatibleEmploye);
                }
                listeCompatibilite.add(missionEmployeJour);
            }
        }


//        for (int i = 0; i < listeCompatibilite.size(); i++) {
//            System.out.println("***************************************************");
//            System.out.println("Employé " + (i + 1));
//            System.out.println("***************************************************");
//            for (int j = 0; j < 5; j++) {
//                System.out.println("Jour : " + (j + 1));
//                for (Mission mission : listeCompatibilite.get(i).get(j)) {
//                    System.out.println("Mission : " + mission.getId());
//                }
//            }
//        }

        return listeCompatibilite;
    }


// avec cluster mais ne marche pas pour 66mission
//    public List<List<List<Mission>>> getMissionsCompatiblesParJourEtCentre(int centreIndex, int jour) {
//        List<List<List<Mission>>> listeCompatibilite = new ArrayList<>();
//
//        List<Employé> centre = employésParCentres.get(centreIndex);
//        for (Employé employe : centre) {
//            List<List<Mission>> missionEmployeJour = new ArrayList<>();
//            for (int j = 0; j < 5; j++) {
//                List<Mission> missionCompatibleEmploye = new ArrayList<>();
//                for (Mission mission : listesMissionsCluster.get(centreIndex)) {
//                    if (Integer.parseInt(mission.getJour()) == (j + 1) && employe.getCompétence().equals(mission.getCompétence())) {
//                        missionCompatibleEmploye.add(mission);
//                    }
//                }
//                missionEmployeJour.add(missionCompatibleEmploye);
//            }
//            listeCompatibilite.add(missionEmployeJour);
//        }
//
//        for (int i = 0; i < listeCompatibilite.size(); i++) {
//            System.out.println("***************************************************");
//            System.out.println("Employé " + (i + 1));
//            System.out.println("***************************************************");
//            System.out.println("Jour : " + jour);
//            for (Mission mission : listeCompatibilite.get(i).get(jour - 1)) {
//                System.out.println("Mission : " + mission.getId());
//            }
//        }
//
//        return listeCompatibilite;
//    }


    public List<List<List<Mission>>> getMissionsCompatiblesParJourEtCentre(int centreIndex, int jour) {
        List<List<List<Mission>>> listeCompatibilite = new ArrayList<>();

        List<Employé> centre = employésParCentres.get(centreIndex);
        for (Employé employe : centre) {
            List<List<Mission>> missionEmployeJour = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                List<Mission> missionCompatibleEmploye = new ArrayList<>();

                List<Mission> listeMissionaUtiliser = new ArrayList<>();
                if (this.employesMemeCentreMemeLangue() == false){
                    listeMissionaUtiliser = listesMissionsCluster.get((employe.getCentreID()-1));
                }else{
                    listeMissionaUtiliser = missions;
                }
                for (Mission mission : listeMissionaUtiliser) {
                    if (Integer.parseInt(mission.getJour()) == (j + 1) && employe.getCompétence().equals(mission.getCompétence())) {
                        missionCompatibleEmploye.add(mission); // verification competence et jour
                    }
                }
                missionEmployeJour.add(missionCompatibleEmploye);
            }
            listeCompatibilite.add(missionEmployeJour);
        }

//        for (int i = 0; i < listeCompatibilite.size(); i++) {
//            System.out.println("***************************************************");
//            System.out.println("Employé " + (i + 1)); //ne correspond pas à l'id de l'employé dans le centre mais a sa position dans la liste
//            System.out.println("***************************************************");
//            System.out.println("Jour : " + jour);
//            for (Mission mission : listeCompatibilite.get(i).get(jour - 1)) {
//                System.out.println("Mission : " + mission.getId());
//            }
//        }

        return listeCompatibilite;
    }

    public List<CoupleEmployéMission> créerCouplesEmployéMission(int centreIndex, int jour) {
        List<CoupleEmployéMission> couples = new ArrayList<>();

        List<List<List<Mission>>> listeCompatibilite = getMissionsCompatiblesParJourEtCentre(centreIndex, jour);
        List<Employé> centre = employésParCentres.get(centreIndex);
        for (int i = 0; i < centre.size(); i++) {
            List<Mission> missionsCompatibles = listeCompatibilite.get(i).get(jour - 1);
            for (Mission mission : missionsCompatibles) {
                couples.add(new CoupleEmployéMission(centre.get(i), mission));
            }
        }

//        for (CoupleEmployéMission couple : couples) {
//            System.out.println("***************************************************");
//            System.out.println("Employé : " + couple.getEmployé().getId());
//            System.out.println("Mission : " + couple.getMission().getId());
//            System.out.println("***************************************************");
//        }


        return couples;
    }


    public double findDistance(String type1, String type2, int id1, int id2) {
        int recherche1 = id1;
        int recherche2 = id2;

        if (type1.equals("mission")) {
            recherche1 = recherche1 + nbclusters - 1;
        } else {
            recherche1--;
        }

        if (type2.equals("mission")) {
            recherche2 = recherche2 + nbclusters - 1;
        } else {
            recherche2--;
        }
        return distances[recherche1][recherche2];
    }


    public boolean employesMemeCentreMemeLangue(){
        boolean memeLangue = false;

        for (int i = 0; i< this.nbclusters ; i++){
            int totLangueLSF = 0;
            int totLangueLPC = 0;
            for (Employé e : employésParCentres.get(i)){
                if (e.getCompétence().equals("LSF")){
                    totLangueLSF +=1;
                }else{
                    totLangueLPC +=1;
                }
            }
            if (totLangueLSF == 0 || totLangueLPC == 0){
                memeLangue = true;
            }
        }

        return memeLangue;
    }





}
