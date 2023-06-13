import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Centre {

    private int id;
    private String nom;
    private int capacite;
    private List<Mission> affectation;

    private List<Employé> employe;
    private double distanceTrajets;

    public Centre(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.affectation = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }


    public double getDistanceTrajets() {
        return distanceTrajets;
    }

    public void setDistanceTrajets(double distanceTrajets) {
        this.distanceTrajets = distanceTrajets;
    }

    public void addMissionAffectee(Mission value) {
        this.affectation.add(value);
    }

    public void addEmploye(Employé value) {
        this.employe.add(value);
    }

    public String toStringCentre() {
        return "Centre{" +
                "id=" + id +
                ", nom='" + nom +
                ", capacite=" + capacite +
                ", distanceTrajets=" + distanceTrajets +
                '}';
    }
}
