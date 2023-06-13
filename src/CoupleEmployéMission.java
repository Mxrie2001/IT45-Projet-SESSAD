public class CoupleEmployéMission {
    private Employé employé;
    private Mission mission;

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
