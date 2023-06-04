import java.util.List;

public class AlgoTabou {
    private List<List<Mission>> missions;
    private List<Employé> employes;
    private List<Centre> centres;

    private int nbClusters;


    public AlgoTabou(List<List<Mission>> missions, List<Employé> employes, List<Centre> centres, int nbClusters) {
        this.missions = missions;
        this.employes = employes;
        this.centres = centres;
        this.nbClusters = nbClusters;
    }

    public String toStringAlgoTabou() {
        return "AlgoTabou{" +
                "Liste Missions =" + this.missions +
                ",Liste Employés='" + this.employes +
                ",Liste Centres='" + this.centres +
                ",Nbre de cluster='" + this.nbClusters +
                '}';
    }



}
