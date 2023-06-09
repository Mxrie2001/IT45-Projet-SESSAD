import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();

        List<Mission> missions = csvReader.CreateMissions("./src/instances/30Missions-2centres/Missions.csv");

        List<Centre> centres = csvReader.CreateCenter("./src/instances/30Missions-2centres/centers.csv");
        List<Employé> employes = csvReader.CreateEmploye("./src/instances/30Missions-2centres/Employees.csv");
//        employes.get(1).getEmployéEdt().AfficherTab(employes.get(1).getEmployéEdt().getDispo1());
        double[][] distanceMatrix = csvReader.createDistanceMatrix("./src/instances/30Missions-2centres/distances.csv");
        Kmeans kmeans = new Kmeans(distanceMatrix, centres, missions, centres.size());
        AlgoTabou tabou = new AlgoTabou(kmeans.getListesMissionsCluster(), employes, centres, centres.size());

        System.out.println("\n************************************************************************");
        System.out.println("Affichage des objets");
        System.out.println("************************************************************************");

        for (Mission mission : missions) {
            System.out.println(mission.toStringMissions());
        }

        for (Centre centre : centres) {
            if (centre.getId() == 1) {
                centre.setCapacite(csvReader.instanceCentre[0]);
            }
            if (centre.getId() == 2) {
                centre.setCapacite(csvReader.instanceCentre[1]);
            }
            if (centre.getId() == 3) {
                centre.setCapacite(csvReader.instanceCentre[2]);
            }
            System.out.println(centre.toStringCentre());

        }



        for (Employé employe : employes) {
            System.out.println(employe.toStringEmploye());
        }


        System.out.println("\n************************************************************************");
        System.out.println("Algorithme Kmeans");
        System.out.println("************************************************************************");

//        System.out.println(kmeans.toStringKmeans());
        kmeans.kmeansAlgorithme();


        System.out.println("\n************************************************************************");
        System.out.println("Algorithme Tabou");
        System.out.println("************************************************************************");

        System.out.println(tabou.toStringAlgoTabou());
        tabou.repartitionEmployéCentre();

        System.out.println("\n************************************************************************");
        System.out.println("Algorithme Tabou Sortie Missiono/Employés");
        System.out.println("************************************************************************");

        tabou.affectationEmployes();

        System.out.println("\n************************************************************************");
        System.out.println("Algorithme Kmeans pour missions");
        System.out.println("************************************************************************");
        kmeans.findClosestMissions2(missions.get(29-1));
        System.out.println(missions.get(29-1).getId());


    }
}