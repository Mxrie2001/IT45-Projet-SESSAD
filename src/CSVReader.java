import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private String csvFilePath;

    public CSVReader(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public List<Mission> readMissions() {
        List<Mission> missions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    int id = Integer.parseInt(values[0]);
                    String jour = values[1];
                    String heureDebut = values[2];
                    String heureFin = values[3];
                    String competence = values[4];
                    String spe = values[5];

                    Mission mission = new Mission(id, jour, heureDebut, heureFin, competence, spe);
                    missions.add(mission);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return missions;
    }
}