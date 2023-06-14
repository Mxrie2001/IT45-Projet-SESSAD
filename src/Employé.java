import java.util.ArrayList;
import java.util.List;

public class Employé {

    private int id;
    private int centreID;
    private String compétence;
    private String spé;
    private EmployéEdt employéEdt;
    private List<Mission> affectation;
    private double distanceTrajets;

    private int score;

    private double distanceJ1 =0.0;
    private double distanceJ2 =0.0;
    private double distanceJ3 =0.0;
    private double distanceJ4 =0.0;
    private double distanceJ5 =0.0;

    private double distanceTotal = 0.0;

    public Employé(int id, int centreID, String compétence, String spé, EmployéEdt employéEdt, double distanceTrajets) {
        this.id = id;
        this.centreID = centreID;
        this.compétence = compétence;
        this.spé = spé;
        this.employéEdt = employéEdt;
        this.affectation = new ArrayList<>();
        this.distanceTrajets = distanceTrajets;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public String getSpé() {
        return spé;
    }

    public void setSpé(String spé) {
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

    public void addMissionAffectee(Mission value) {
        this.affectation.add(value);
    }

    public void removeMissionFromAffectation(Mission mission) {
        affectation.remove(mission);
    }

    public boolean isInAffectation(Mission mission) {
        return affectation.contains(mission);
    }

    public Mission getLastMissionAffectee() {
        if (this.affectation != null && !this.affectation.isEmpty()) {
            return this.affectation.get(this.affectation.size() - 1);
        } else {
            return null;
        }
    }

    public double getDistanceJ1() {
        return distanceJ1;
    }

    public void setDistanceJ1(double distanceJ1) {
        this.distanceJ1 = distanceJ1;
    }

    public double getDistanceJ2() {
        return distanceJ2;
    }

    public void setDistanceJ2(double distanceJ2) {
        this.distanceJ2 = distanceJ2;
    }

    public double getDistanceJ3() {
        return distanceJ3;
    }

    public void setDistanceJ3(double distanceJ3) {
        this.distanceJ3 = distanceJ3;
    }

    public double getDistanceJ4() {
        return distanceJ4;
    }

    public void setDistanceJ4(double distanceJ4) {
        this.distanceJ4 = distanceJ4;
    }

    public double getDistanceJ5() {
        return distanceJ5;
    }

    public void setDistanceJ5(double distanceJ5) {
        this.distanceJ5 = distanceJ5;
    }

    public double getDistanceTotal() {
        return distanceTotal;
    }

    public void calculateDistanceTotal() {
        this.distanceTotal = this.distanceJ1 + this.distanceJ2 + this.distanceJ3 + this.distanceJ4 + this.distanceJ5;
    }

    public String toStringEmploye() {
        return "Employe{" +
                "id=" + id +
                ", centreID='" + centreID + '\'' +
                ", competence=" + compétence + '\'' +
                ", specialité=" + spé + '\''+
                ", employéEdt=" + employéEdt.toStringEmployeEDT();

    }
}
