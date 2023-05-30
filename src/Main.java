import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        CSVReader csvReader = new CSVReader();

        List<Mission> missions = csvReader.CreateMissions("./src/instances/30Missions-2centres/Missions.csv");

        List<Centre> centres = csvReader.CreateCenter("./src/instances/30Missions-2centres/centers.csv");
        List<Employé> employes = csvReader.CreateEmploye("./src/instances/30Missions-2centres/Employees.csv");

        for (Mission mission : missions) {
            System.out.println(mission.toStringMissions());
        }

        for (Centre centre : centres) {
            System.out.println(centre.toStringCentre());
        }

        for (Employé employe : employes) {
            System.out.println(employe.toStringEmploye());
        }
    }
}