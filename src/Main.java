import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();

        List<Mission> missions = csvReader.CreateMissions("./src/instances/30Missions-2centres/Missions.csv");

        List<Centre> centres = csvReader.CreateCenter("./src/instances/30Missions-2centres/centers.csv");
        List<Employé> employes = csvReader.CreateEmploye("./src/instances/30Missions-2centres/Employees.csv");
        double[][] distanceMatrix = csvReader.createDistanceMatrix("./src/instances/30Missions-2centres/distances.csv");

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

        // Affichage de la matrice des distances
        int numRows = distanceMatrix.length;
        int numCols = distanceMatrix[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(distanceMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}