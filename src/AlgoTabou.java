import java.util.ArrayList;
import java.util.List;

public class AlgoTabou {
    private List<List<Mission>> missions;

    private List<List<Employé>> employésParCentres;
    private List<Employé> employes;
    private List<Centre> centres;

    private int nbClusters;


    public AlgoTabou(List<List<Mission>> missions, List<Employé> employes, List<Centre> centres, int nbClusters) {
        this.missions = missions;
        this.employes = employes;
        this.centres = centres;
        this.nbClusters = nbClusters;
        this.employésParCentres = new ArrayList<>();
    }

    public String toStringAlgoTabou() {
        return "AlgoTabou{" +
                "Liste Missions =" + this.missions +
                ",Liste Employés='" + this.employes +
                ",Liste Centres='" + this.centres +
                ",Nbre de cluster='" + this.nbClusters +
                '}';
    }

    public void repartitionEmployéCentre() {
        for (Employé employe : employes) {
            boolean isNewCentre = true;
            for (List<Employé> centre : employésParCentres) {
                if (!centre.isEmpty() && centre.get(0).getCentreID() == employe.getCentreID()) {
                    centre.add(employe);
                    isNewCentre = false;
                    break;
                }
            }
            if (isNewCentre) {
                List<Employé> nouveauCentre = new ArrayList<>();
                nouveauCentre.add(employe);
                employésParCentres.add(nouveauCentre);
            }
        }

        // Affichage des employés par centres
        System.out.println("\n************************************************************************");
        System.out.println("Repartition employés par centres");
        System.out.println("************************************************************************");

        for (int i = 0; i < employésParCentres.size(); i++) {
            List<Employé> centre = employésParCentres.get(i);
            System.out.println("Centre " + (i + 1) + ":");
            for (Employé employe : centre) {
                System.out.println("Employé n°" + employe.getId() + ", ID Centre : " + employe.getCentreID());
            }
            System.out.println();
        }

    }

    public void affectationEmployes(){
        // Affectation des missions aux employés pour chaque cluster
        for (int i = 0; i < this.nbClusters; i++) {
            List<Mission> mission = this.missions.get(i);
            List<Employé> employés = this.employésParCentres.get(i);

            for (int j = 0; j < mission.size(); j++) {
                Employé bestEmploye;
                for (Employé employeCentre : employés) {
                    if (employeCentre.getCompétence() == mission.get(j).getCompétence()) {
                        bestEmploye = employeCentre;


                    }
                }


            }




        }
    }












}
