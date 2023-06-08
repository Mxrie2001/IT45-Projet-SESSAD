import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {


    int[] instanceCentre = new int[3];

    public CSVReader() {
    }

    public List<Mission> CreateMissions(String csvFilePath) {
        List<Mission> missions = new ArrayList<>();
        int i = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    int id = i;
                    String jour = values[1];
                    String heureDebut = values[2];
                    String heureFin = values[3];
                    String competence = values[4];
                    String spe = values[5];

                    Mission mission = new Mission(id, jour, heureDebut, heureFin, competence, spe);
                    missions.add(mission);
                    i += 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return missions;
    }


    public List<Centre> CreateCenter(String csvFilePath) {
        List<Centre> centres = new ArrayList<>();
        int i = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    int id = i;
                    String nom = values[1];

                    Centre centre = new Centre(id, nom);
                    centres.add(centre);
                    i += 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return centres;
    }

    public List<Employé> CreateEmploye(String csvFilePath)
    {
        List<Employé> employes = new ArrayList<>();
        int i = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    int id =  i;
                    int centreID =  Integer.parseInt(values[1]);
                    String compétence = values[2];
                    String spé = values[3];


                    if (centreID == 1){
                        instanceCentre[0] += 1;
                    }
                    if (centreID == 2){
                        instanceCentre[1] += 1;
                    }
                    if (centreID == 3){
                        instanceCentre[2] += 1;
                    }

                    Employé employe = new Employé(id, centreID, compétence, spé, new EmployéEdt(), 0.0);
                    employes.add(employe);
                    i += 1;

                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return employes;
    }


    public double[][] createDistanceMatrix(String csvFilePath) {
        double[][] distanceMatrix = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            List<String[]> rows = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                rows.add(values);
            }

            int numRows = rows.size();
            int numCols = rows.get(0).length;
            distanceMatrix = new double[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                String[] row = rows.get(i);
                for (int j = 0; j < numCols; j++) {
                    distanceMatrix[i][j] = Double.parseDouble(row[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return distanceMatrix;
    }
}