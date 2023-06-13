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
        int[][] CentreMission = new int[this.distances.length - 2][this.nbclusters];

        // on commence à la 2 car on ne veut pas distances des centres entre eux
        for (int i = 2; i < this.distances.length; i++) {
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

            CentreMission[i-2][0] = i-1;
            CentreMission[i-2][1] = minCol+1;
        }


        System.out.println("\n************************************************************************");
        System.out.println("Affichage Tableau Mission -> Centre en fonction de la distance la plus courte");
        System.out.println("************************************************************************");
//        System.out.println("Tableau Centre Missions:");
        for (int i = 0; i < CentreMission.length; i++) {
            System.out.println(CentreMission[i][0] + "\t" + CentreMission[i][1]);
        }


        for (int i = 0; i < this.nbclusters; i++) {
            List<Mission> listeObjets = new ArrayList<>();
            for (int j = 0; j < CentreMission.length; j++) {
                if (CentreMission[j][1] == i+1) {
//                    System.out.println("mission: " +CentreMission[j][0] + "\t Centre: " + CentreMission[j][1]);
                    // Ajoutez les objets souhaités à chaque liste
                    listeObjets.add(missions.get(CentreMission[j][0] -1));
//                    System.out.println(missions.get(CentreMission[j][0] -1).getId());
                }
            }
            listesMissionsCluster.add(listeObjets);
        }

        System.out.println("\n************************************************************************");
        System.out.println("Affichage des Clusters");
        System.out.println("************************************************************************");

        for (int i = 0; i < listesMissionsCluster.size(); i++) {
            List<Mission> listeObjets = listesMissionsCluster.get(i);
            System.out.println("Cluster " + (i + 1) + ":");
            for (Mission objet : listeObjets) {
                System.out.println("Mission n°" + objet.getId());
            }
            System.out.println();
        }

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
        System.out.println("\n************************************************************************");
        System.out.println("Repartition employés par centres");
        System.out.println("************************************************************************");

        for (int i = 0; i < employésParCentres.size(); i++) {
            List<Employé> centre = employésParCentres.get(i);
            System.out.println("Centre " + (i + 1) + ":");
            for (Employé employe : centre) {
                System.out.println("Employé n°" + employe.getId() + ", ID Centre : " + employe.getCentreID());
            }
            System.out.println();
        }

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
//            System.out.println("Employé " + (i + 1) + "\n");
//            for (int j = 0; j < 5; j++) {
//                System.out.println("Jour : " + (j + 1));
//                for (Mission mission : listeCompatibilite.get(i).get(j)) {
//                    System.out.println("Mission : " + mission.getId());
//                }
//            }
//        }

        return listeCompatibilite;
    }












    public Map<Mission, Double> findClosestMissionsToCentreJ(int clusterNum, String missionDay) {
        int[][] centreMission = new int[this.distances.length - 2][this.nbclusters];
        Map<Mission, Double> missionDistances = new HashMap<>();

        // on commence à la 2 car on ne veut pas des distances des centres entre eux
        for (int i = 2; i < this.distances.length; i++) {
            double minimum = this.distances[i][0];
            int minCol = 0;

            // calcul la distance minimale entre les centres et la mission sélectionnée
            for (int j = 1; j < this.nbclusters; j++) {
                if (this.distances[i][j] < minimum) {
                    minimum = this.distances[i][j];
                    minCol = j;
                }
            }

            centreMission[i-2][0] = i-1;
            centreMission[i-2][1] = minCol+1;
        }

        // Recherche des missions les plus proches du même jour dans le cluster spécifié
        for (int i = 0; i < centreMission.length; i++) {
            if (centreMission[i][1] == clusterNum && missions.get(centreMission[i][0] - 1).getJour().equals(missionDay)) {
                double distance = this.distances[centreMission[i][0]][centreMission[i][1] - 1];
                missionDistances.put(missions.get(centreMission[i][0] - 1), distance);
            }
        }

        // Tri des missions en fonction de leur distance (du plus proche au plus éloigné)
        List<Map.Entry<Mission, Double>> sortedMissions = new ArrayList<>(missionDistances.entrySet());
        sortedMissions.sort(Map.Entry.comparingByValue());

        Map<Mission, Double> sortedMissionDistances = new LinkedHashMap<>();
        for (Map.Entry<Mission, Double> entry : sortedMissions) {
            sortedMissionDistances.put(entry.getKey(), entry.getValue());
        }

        return sortedMissionDistances;
    }


//
//    public Map<Mission, Double> findClosestMissiontoCentreJ(int clusterNum, String missionDay) {
//        int[][] centreMission = new int[this.distances.length - 2][this.nbclusters];
//        Map<Mission, Double> missionReturn = new HashMap<>();
//        // on commence à la 2 car on ne veut pas distances des centres entre eux
//        for (int i = 2; i < this.distances.length; i++) {
//            double minimum = this.distances[i][0];
//            int minCol = 0;
//
//            // calcul la distance minimale entre les centres et la mission sélectionnée
//            for (int j = 1; j < this.nbclusters; j++) {
//                if (this.distances[i][j] < minimum) {
//                    minimum = this.distances[i][j];
//                    minCol = j;
//                }
//            }
//
//            centreMission[i-2][0] = i-1;
//            centreMission[i-2][1] = minCol+1;
//        }
//
//        // Recherche de la mission la plus proche du même jour dans le cluster spécifié
//        int closestMissionIndex = -1;
//        double closestDistance = Double.MAX_VALUE;
//        for (int i = 0; i < centreMission.length; i++) {
//            if (centreMission[i][1] == clusterNum && missions.get(centreMission[i][0] - 1).getJour().equals(missionDay)) {
//                double distance = this.distances[centreMission[i][0]][centreMission[i][1] - 1];
//                if (distance < closestDistance) {
//                    closestDistance = distance;
//                    closestMissionIndex = centreMission[i][0] - 1;
//                }
//            }
//        }
//
//        if (closestMissionIndex != -1) {
//            System.out.println("mission la plus proche du centre "+ clusterNum+ " au jour "+ missionDay + " est : "+ closestMissionIndex + " à " + closestDistance);
//            missionReturn.put(missions.get(closestMissionIndex), closestDistance);
//
//        }
//        return missionReturn;
//    }
//


//    public List<Mission> findClosestMissions(Mission mission) {
//        int clusterIndex = -1;
//        List<List<Mission>> listesMissionsCluster2 = listesMissionsCluster;
//
//        // Trouver le cluster de la mission donnée
//        for (int i = 0; i < listesMissionsCluster2.size(); i++) {
//            if (listesMissionsCluster2.get(i).contains(mission)) {
//                clusterIndex = i;
//                break;
//            }
//        }
//
//        if (clusterIndex == -1) {
//            System.out.println("La mission donnée n'appartient à aucun cluster");
//            return null;
//        }
//
//        // Liste des missions du cluster
//        List<Mission> clusterMissions = listesMissionsCluster2.get(clusterIndex);
//
//        // Calculer les distances de la mission donnée par rapport aux autres missions du cluster
//        double[] distancesComp = new double[clusterMissions.size()];
//        int distanceIndex = 0;
//        System.out.println("Distances entre la mission donnée et les autres missions du même cluster:");
//        int IdexMissionCours=0;
//        for (int i = 0; i < clusterMissions.size(); i++) {
//            Mission otherMission = clusterMissions.get(i);
//            // Utilisez votre matrice de distances ici
//            if (otherMission == mission) {IdexMissionCours = i;}
//            double distance = distances[mission.getId() + nbclusters - 1][otherMission.getId() + nbclusters - 1];
//            distancesComp[distanceIndex] = distance;
//            distanceIndex++;
//            System.out.println("Distance entre la mission " + mission.getId() + " et la mission " + otherMission.getId() + ": " + distance);
//
//        }
////        for (Mission objet : clusterMissions) {
////            System.out.println("Mission ID: " + objet.getId());
////        }
//        // Trouver les deux missions les plus proches
//        List<Mission> closestMissions = new ArrayList<>();
//        int minIndex1 = -1;
//        int minIndex2 = -1;
//        double minDistance1 = Double.MAX_VALUE;
//        double minDistance2 = Double.MAX_VALUE;
//
//        for (int i = 0; i < distancesComp.length; i++) {
//            if (i != IdexMissionCours && !clusterMissions.get(i).isAffected()){
//                if (distancesComp[i] < minDistance1) {
//                    minDistance2 = minDistance1;
//                    minIndex2 = minIndex1;
//                    minDistance1 = distancesComp[i];
//                    minIndex1 = i;
//                } else if (distancesComp[i] < minDistance2) {
//                    minDistance2 = distancesComp[i];
//                    minIndex2 = i;
//                }
//            }
//        }
////        System.out.println("Tableau distancesComp:");
////        for (int i = 0; i < distancesComp.length; i++) {
////            System.out.println("Distance " + i + ": " + distancesComp[i]);
////        }
////        System.out.println(distancesComp[minIndex1]);
////        System.out.println(minIndex1 +  "      "+minIndex2);
//        // Ajouter les deux missions les plus proches à la liste de résultat
//        closestMissions.add(clusterMissions.get(minIndex1));
//        closestMissions.add(clusterMissions.get(minIndex2));
////        System.out.println(clusterMissions.get(minIndex1));
//        System.out.println("\n************************************************************************");
//        System.out.println("Missions les plus proches:");
//        System.out.println("************************************************************************");
//
////        for (Mission objet : closestMissions) {
////            System.out.println("Mission ID: " + objet.getId());
////        }
//        // Afficher les missions les plus proches
//        System.out.println("Mission la plus proche --> " + closestMissions.get(0).getId());
//        System.out.println("Mission la deuxième plus proche --> " + closestMissions.get(1).getId());
//
//        return closestMissions;
//    }


    public Map<Mission, Double> findClosestMissions2(Mission mission) {
        Map<Mission, Double> missionReturn = new HashMap<>();
        int distanceIndex = 0;
        int clusterIndex = -1;
        List<List<Mission>> listesMissionsCluster2 = listesMissionsCluster;

        // Trouver le cluster de la mission donnée
        for (int i = 0; i < listesMissionsCluster2.size(); i++) {
            if (listesMissionsCluster2.get(i).contains(mission)) {
                clusterIndex = i;
                break;
            }
        }

        if (clusterIndex == -1) {
            System.out.println("La mission donnée n'appartient à aucun cluster");
            return null;
        }

        // Liste des missions du cluster
        List<Mission> clusterMissions = listesMissionsCluster2.get(clusterIndex);
        List<Mission> MissionOKListe = new ArrayList<>();
        // Calculer les distances de la mission donnée par rapport aux autres missions du cluster
        double[] distancesComp = new double[clusterMissions.size()];
        System.out.println("Distances entre la mission donnée et les autres missions du même cluster:");
        Mission closestMission = null;
        for (Mission otherMission : clusterMissions) {
            if ((otherMission.isAffected() == false) && (otherMission.getId() != mission.getId()) && (otherMission.VerifJour(otherMission, mission) == true)) {
                MissionOKListe.add(otherMission);
            }
        }

        if(MissionOKListe !=null){
            double minDistance = Double.MAX_VALUE;
            double closestDistance =0;
            for (Mission missionOK : MissionOKListe) {
                double distance = distances[mission.getId() + nbclusters - 1][missionOK.getId() + nbclusters - 1];
                distancesComp[distanceIndex] = distance;
                System.out.println("Distance entre la mission " + mission.getId() + " et la mission " + missionOK.getId() + ": " + distance);

                if (distancesComp[distanceIndex]<minDistance) {
                    closestMission = missionOK;
                    minDistance=distancesComp[distanceIndex];
                }
                distanceIndex++;
                System.out.println(distanceIndex);
            }

            System.out.println("\n************************************************************************");
            System.out.println("Missions la plus proche:");
            System.out.println("************************************************************************");
            System.out.println("Mission la plus proche --> " + closestMission.getId());

            missionReturn.put(closestMission, minDistance);
            for (Map.Entry<Mission, Double> entry : missionReturn.entrySet()) {
                Mission mission3 = entry.getKey();
                double distance = entry.getValue();
                System.out.println("Mission: " + mission3.getId() + ", Distance: " + distance);
            }
        }

        return missionReturn;

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
}
