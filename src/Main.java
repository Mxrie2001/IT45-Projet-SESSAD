import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        //Pour avoir le temps d'execution --> début du chnono
        long tempsDebut = System.currentTimeMillis();


        // Création des objets
        CSVReader csvReader = new CSVReader();

        List<Mission> missions = csvReader.CreateMissions("./src/instances/200Missions-2centres/Missions.csv");

        List<Centre> centres = csvReader.CreateCenter("./src/instances/200Missions-2centres/centres.csv");
        List<Employé> employes = csvReader.CreateEmploye("./src/instances/200Missions-2centres/Employees.csv");
        double[][] distanceMatrix = csvReader.createDistanceMatrix("./src/instances/200Missions-2centres/distances.csv");
        Kmeans kmeans = new Kmeans(distanceMatrix, employes, centres, missions, centres.size());
        AlgoTabou tabou = new AlgoTabou(kmeans.getListesMissionsCluster(), distanceMatrix, employes, centres, centres.size(), kmeans, missions);

        //Création des clusters
        kmeans.kmeansAlgorithme();
        kmeans.repartitionEmployéCentre();

        for(int centre = 0; centre < centres.size(); centre++){

            for (int jour = 1; jour <=5; jour++){
                tabou.comparerEtSupprimerDoublonsMission(centre,jour); //recherche de couple optimaux
            }
        }

        //Affectation des missions non affecté et suppression des doublons
        tabou.affecterMissionPasAffectees(tabou.RetourneMissionPasAffectee());
        tabou.suppressionDoublonApresAffectation(tabou.AllMissionaffectedDoublons());

        System.out.println("\n************************************************************************");
        System.out.println("Résultats optimaux pour chaques employés");
        System.out.println("************************************************************************");
        tabou.affichageCheminOptimaux();


        //vérification des résultats
        tabou.verifAlgoOK();

        //Autres vérifs
        System.out.println("Toute les missions affecté?" + tabou.verifAllMissionaffected());
        System.out.println("Affichage des doublons du resultat : " + tabou.AllMissionaffectedDoublons());
        System.out.println("Affichage des missions pas affecté : " + tabou.RetourneMissionPasAffectee());

        //Pour avoir le temps d'execution --> fin du chono et affichage
        long tempsFin = System.currentTimeMillis();
        long tempsExecution = tempsFin - tempsDebut;

        System.out.println("\n \nTemps d'exécution : " + tempsExecution + " millisecondes");



    }

}