public class Centre {

    private int id;
    private String nom;
    private int capacite;
    private String[] affectation;
    private double distanceTrajets;

    public Centre(int id, String nom, int capacite, String[] affectation, double distanceTrajets) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.affectation = affectation;
        this.distanceTrajets = distanceTrajets;
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
}
