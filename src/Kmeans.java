import java.util.*;

import static java.lang.Integer.parseInt;

public class Kmeans {


    private double[][] distances;           // Matrice de distance pré-calculée
    private int[] clusters;                 // Tableau de listes pour stocker les points dans chaque cluster

    private int nbclusters;                 // Nombre de clusters -> centres

    private List<Centre> centres;           // Liste de tous centres

    private List<Mission> missions;         // Liste de toutes missions

    private List<List<Mission>> listesMissionsCluster;  // Liste de missions par centre

    private List<List<Employé>> employésParCentres;     // Liste d'employés par cnetre

    private List<Employé> employes;         // Liste de tous les employés

    private List<List<Object>> employesMissionsCommunes = new ArrayList<>();

    //Constructeur
    public Kmeans(double[][] distances, List<Employé> employes, List<Centre> centres, List<Mission> missions, int nbclusters)
    {
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

    public void setListesMissionsCluster(List<List<Mission>> listesMissionsCluster) { this.listesMissionsCluster = listesMissionsCluster; }

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

    //retourne une chaine de caractères pour afficher les informations du kmeans
    public String toStringKmeans()
    {
        printMatrix(this.distances);
        return "Kmeans{" +
                "nbcluster=" + this.nbclusters +
                ", centres liste='" + this.centres +
                '}';
    }

    // Fonction qui affiche la matrice des distances
    public void printMatrix(double[][] distanceMatrix)
    {
        int numRows = distanceMatrix.length;
        int numCols = distanceMatrix[0].length;

        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                System.out.print(distanceMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Fonction principale du K-means -> créer des clusters en affectant les missions aux centres les plus proches
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

            CentreMission[i-this.nbclusters][0] = i-this.nbclusters+1;
            CentreMission[i-this.nbclusters][1] = minCol+1;
        }


        for (int i = 0; i < this.nbclusters; i++) {
            List<Mission> listeObjets = new ArrayList<>();
            for (int j = 0; j < CentreMission.length; j++) {
                if (CentreMission[j][1] == i+1) {
                    // Ajoutez les objets souhaités à chaque liste
                    listeObjets.add(missions.get(CentreMission[j][0] -1));
                }
            }
            listesMissionsCluster.add(listeObjets);
        }

    }

    // Fonction qui répartie les employés selon leurs centres
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

    }


//    Fonction retournant les missions compatible à chaques employé par jour et par centres
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

        return listeCompatibilite;
    }

    //Fonction qui créer les couples employé/mission
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

        return couples;
    }


    // Fonction pour trouver la distance entre deux objets dans la matrice des distances
    // Le type est là pour verifier si c'est un centre ou une mission
    // Une mission et un centre ont des index différents de ceux présents dans la matrice des distances
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

    // Vérifie si les employés d'un meme centre ont la meme compétence, pour tout les centres
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
