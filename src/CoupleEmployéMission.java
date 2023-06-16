public class CoupleEmployéMission {

    private Employé employé;        // Employé compatible avec mission
    private Mission mission;        //Mission compatible avec employé

    //Constructeur
    public CoupleEmployéMission(Employé employé, Mission mission) {
        this.employé = employé;
        this.mission = mission;
    }

    //Setters et getters
    public Employé getEmployé() {
        return employé;
    }

    public void setEmployé(Employé employé) {
        this.employé = employé;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
}
