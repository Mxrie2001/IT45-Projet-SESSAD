import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kmeans {


    private double[][] distances; // Matrice de distance précalculée
    private int[] clusters; // Tableau de listes pour stocker les points dans chaque cluster

    private int nbclusters; // Nombre de clusters

    private List<Centre> centres; // Liste de centres

    private List<Mission> missions; // Liste de missions

    private List<Mission>[] listesClusters;

    private Mission[] listesClustersM;




    public Kmeans(double[][] distances, List<Centre> centres, List<Mission> missions, int nbclusters) {
        this.distances = distances;
        this.centres = centres;
        this.nbclusters = nbclusters;
        this.missions = missions;
        this.listesClusters = new List[0];
        this.listesClustersM = new Mission[0];
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

            System.out.println("Le minimum de la ligne " + (i + 1) + " est : " + minimum + " dans la colonne " + (minCol + 1));

            CentreMission[i-2][0] = i-1;
            CentreMission[i-2][1] = minCol+1;
        }

        System.out.println("Tableau Centre Missions:");
        for (int i = 0; i < CentreMission.length; i++) {
            System.out.println(CentreMission[i][0] + "\t" + CentreMission[i][1]);
        }

        System.out.println(this.missions.get(CentreMission[0][0]));
        System.out.println(this.missions.get(CentreMission[10][0]));
        System.out.println(this.missions.get(CentreMission[28][0]));
        System.out.println(missions.size());
        System.out.println("test:" +  CentreMission[29][0]);
        System.out.println(this.missions.get(CentreMission[29][0]));
        int test = 29;
        System.out.println(this.missions.get(test));
        System.out.println(this.distances.length - 2);


        for (int i = 0; i < 29; i++) {
//            for (int j = 0; j < this.nbclusters +1; j++) {
//                if(CentreMission[i][1] == j){
//                    listesClustersM[j] = this.missions.get(CentreMission[i][0]);
//                }
            System.out.println(this.missions.get(CentreMission[i][0]));

//            }
        }

//        System.out.println("Tableau Centre Missions Affectations:");
//        for (int i = 0; i < this.distances.length - 2; i++) {
//            System.out.println(listesClustersM[i] + "\t");
//        }


//         //Nombre de centres
//        int numCenters = nbclusters;
//        List<Integer>[] assignedMissions = new List[numCenters];
//
//        // Initialisation des listes pour chaque centre
//        for (int i = 0; i < numCenters; i++) {
//            assignedMissions[i] = new ArrayList<>();
//        }
//
//        // Parcourir la matrice des affectations et ajouter les missions aux listes correspondantes
//        for (int missionId = 0; missionId < missions.length; missionId++) {
//            int assignedCenterId = assignedCenters[missionId]; // Récupérer l'ID du centre assigné à la mission
//            assignedMissions[assignedCenterId].add(missionId); // Ajouter l'ID de la mission à la liste du centre correspondant
//        }



    }
}
