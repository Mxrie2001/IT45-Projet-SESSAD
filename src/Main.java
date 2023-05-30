import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String csvFilePath = "./src/instances/30Missions-2centres/Missions.csv";
        CSVReader csvReader = new CSVReader(csvFilePath);

        List<Mission> missions = csvReader.readMissions();

        // Faites quelque chose avec la liste des missions créées
        for (Mission mission : missions) {
            System.out.println(mission.toString());
        }
    }
}