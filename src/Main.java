import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        //Pour avoir le temps d'execution --> début du chnono
        long tempsDebut = System.currentTimeMillis();



        CSVReader csvReader = new CSVReader();

        List<Mission> missions = csvReader.CreateMissions("./src/instances/200Missions-2centres/Missions.csv");

        List<Centre> centres = csvReader.CreateCenter("./src/instances/200Missions-2centres/centres.csv");
        List<Employé> employes = csvReader.CreateEmploye("./src/instances/200Missions-2centres/Employees.csv");
//        employes.get(1).getEmployéEdt().AfficherTab(employes.get(1).getEmployéEdt().getDispo1());
        double[][] distanceMatrix = csvReader.createDistanceMatrix("./src/instances/200Missions-2centres/distances.csv");
        Kmeans kmeans = new Kmeans(distanceMatrix, employes, centres, missions, centres.size());
        AlgoTabou tabou = new AlgoTabou(kmeans.getListesMissionsCluster(), distanceMatrix, employes, centres, centres.size(), kmeans, missions);

//        System.out.println("\n************************************************************************");
//        System.out.println("Affichage des objets");
//        System.out.println("************************************************************************");
//
//        for (Mission mission : missions) {
//            System.out.println(mission.toStringMissions());
//        }
//
//        for (Centre centre : centres) {
//            if (centre.getId() == 1) {
//                centre.setCapacite(csvReader.instanceCentre[0]);
//            }
//            if (centre.getId() == 2) {
//                centre.setCapacite(csvReader.instanceCentre[1]);
//            }
//            if (centre.getId() == 3) {
//                centre.setCapacite(csvReader.instanceCentre[2]);
//            }
//            System.out.println(centre.toStringCentre());
//
//        }
//
//
//
//        for (Employé employe : employes) {
//            System.out.println(employe.toStringEmploye());
//        }


//        System.out.println("\n************************************************************************");
//        System.out.println("Algorithme Kmeans");
//        System.out.println("************************************************************************");

        kmeans.kmeansAlgorithme();
        kmeans.repartitionEmployéCentre();

//        kmeans.CompatibiliteMissionEmploye();

//        System.out.println("***********************");
//        kmeans.créerCouplesEmployéMission(0,1); // attention index centre commence à 0 ne correspond pas à l'id du centre

        for(int centre = 0; centre < centres.size(); centre++){
//            System.out.println("**********************");
//            System.out.println("Centre n°"+(centre+1));
//            System.out.println("**********************");

            for (int jour = 1; jour <=5; jour++){
//                System.out.println("****************** Jour "+ jour + " ******************");
                tabou.comparerEtSupprimerDoublonsMission(centre,jour); //recherche de couple optimaux
            }
        }


        tabou.affecterMissionPasAffectees(tabou.RetourneMissionPasAffectee());
        tabou.suppressionDoublonApresAffectation(tabou.AllMissionaffectedDoublons());

        System.out.println("\n************************************************************************");
        System.out.println("Résultats optimaux pour chaques employés");
        System.out.println("************************************************************************");
        tabou.affichageCheminOptimaux();



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