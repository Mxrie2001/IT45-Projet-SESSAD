public class CoupleEmployéMission {

    private Employé employé;        // Employé auquel la mission est affectée
    private Mission mission;        //Mission à laquelle l'employé est affecté

    public CoupleEmployéMission(Employé employé, Mission mission) {
        this.employé = employé;
        this.mission = mission;
    }

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
