import java.util.List;

public class Employé {

    private int id;
    private int centreID;
    private String compétence;
    private List<String> spé;
    private EmployéEdt employéEdt;
    private List<Mission> affectation;
    private double distanceTrajets;


    public Employé(int id, int centreID, String compétence, List<String> spé, EmployéEdt employéEdt, List<Mission> affectation, double distanceTrajets) {
        this.id = id;
        this.centreID = centreID;
        this.compétence = compétence;
        this.spé = spé;
        this.employéEdt = employéEdt;
        this.affectation = affectation;
        this.distanceTrajets = distanceTrajets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCentreID() {
        return centreID;
    }

    public void setCentreID(int centreID) {
        this.centreID = centreID;
    }

    public String getCompétence() {
        return compétence;
    }

    public void setCompétence(String compétence) {
        this.compétence = compétence;
    }

    public List<String> getSpé() {
        return spé;
    }

    public void setSpé(List<String> spé) {
        this.spé = spé;
    }

    public EmployéEdt getEmployéEdt() {
        return employéEdt;
    }

    public void setEmployéEdt(EmployéEdt employéEdt) {
        this.employéEdt = employéEdt;
    }

    public List<Mission> getAffectation() {
        return affectation;
    }

    public void setAffectation(List<Mission> affectation) {
        this.affectation = affectation;
    }

    public double getDistanceTrajets() {
        return distanceTrajets;
    }

    public void setDistanceTrajets(double distanceTrajets) {
        this.distanceTrajets = distanceTrajets;
    }
}
