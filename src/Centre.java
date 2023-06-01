import java.util.Arrays;

public class Centre {

    private int id;
    private String nom;
    private int capacite;
    private String[] affectation;
    private double distanceTrajets;

    public Centre(int id, String nom) {
        this.id = id;
        this.nom = nom;
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

    public String[] getAffectation() {
        return affectation;
    }

    public void setAffectation(String[] affectation) {
        this.affectation = affectation;
    }

    public double getDistanceTrajets() {
        return distanceTrajets;
    }

    public void setDistanceTrajets(double distanceTrajets) {
        this.distanceTrajets = distanceTrajets;
    }

    public void calculateCapacity(){

    }

    public String toStringCentre() {
        return "Centre{" +
                "id=" + id +
                ", nom='" + nom +
                ", capacite=" + capacite +
                ", affectation=" + Arrays.toString(affectation) +
                ", distanceTrajets=" + distanceTrajets +
                '}';
    }
}
