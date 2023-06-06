import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kmeans {


    private double[][] distances; // Matrice de distance précalculée
    private int[] clusters; // Tableau de listes pour stocker les points dans chaque cluster

    private int nbclusters; // Nombre de clusters

    private List<Centre> centres; // Liste de centres

    private List<Mission> missions; // Liste de missions

    private List<List<Mission>> listesMissionsCluster;






    public Kmeans(double[][] distances, List<Centre> centres, List<Mission> missions, int nbclusters) {
        this.distances = distances;
        this.centres = centres;
        this.nbclusters = nbclusters;
        this.missions = missions;
        this.listesMissionsCluster = new ArrayList<>();
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

    public List<Mission> findClosestMissions(Mission mission) {
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

        // Calculer les distances de la mission donnée par rapport aux autres missions du cluster
        double[] distancesComp = new double[clusterMissions.size()];
        int distanceIndex = 0;
        System.out.println("Distances entre la mission donnée et les autres missions du même cluster:");
        int IdexMissionCours=0;
        for (int i = 0; i < clusterMissions.size(); i++) {
            Mission otherMission = clusterMissions.get(i);
            // Utilisez votre matrice de distances ici
            if (otherMission == mission) {IdexMissionCours = i;}
            double distance = distances[mission.getId() + nbclusters - 1][otherMission.getId() + nbclusters - 1];
            distancesComp[distanceIndex] = distance;
            distanceIndex++;
            System.out.println("Distance entre la mission " + mission.getId() + " et la mission " + otherMission.getId() + ": " + distance);

        }
//        for (Mission objet : clusterMissions) {
//            System.out.println("Mission ID: " + objet.getId());
//        }
        // Trouver les deux missions les plus proches
        List<Mission> closestMissions = new ArrayList<>();
        int minIndex1 = -1;
        int minIndex2 = -1;
        double minDistance1 = Double.MAX_VALUE;
        double minDistance2 = Double.MAX_VALUE;

        for (int i = 0; i < distancesComp.length; i++) {
            if (i != IdexMissionCours){
                if (distancesComp[i] < minDistance1) {
                    minDistance2 = minDistance1;
                    minIndex2 = minIndex1;
                    minDistance1 = distancesComp[i];
                    minIndex1 = i;
                } else if (distancesComp[i] < minDistance2) {
                    minDistance2 = distancesComp[i];
                    minIndex2 = i;
                }
            }
        }
//        System.out.println("Tableau distancesComp:");
//        for (int i = 0; i < distancesComp.length; i++) {
//            System.out.println("Distance " + i + ": " + distancesComp[i]);
//        }
        System.out.println(distancesComp[minIndex1]);
        System.out.println(minIndex1 +  "      "+minIndex2);
        // Ajouter les deux missions les plus proches à la liste de résultat
        closestMissions.add(clusterMissions.get(minIndex1));
        closestMissions.add(clusterMissions.get(minIndex2));
//        System.out.println(clusterMissions.get(minIndex1));
        System.out.println("Missions les plus proches:");
//        for (Mission objet : closestMissions) {
//            System.out.println("Mission ID: " + objet.getId());
//        }
        // Afficher les missions les plus proches
        System.out.println("Mission la plus proche --> " + closestMissions.get(0).getId());
        System.out.println("Mission la deuxième plus proche --> " + closestMissions.get(1).getId());

        return closestMissions;
    }

}
