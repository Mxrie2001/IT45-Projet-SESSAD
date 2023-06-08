import java.beans.beancontext.BeanContext;
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
                Employé bestEmploye = null;
                int totalBest = 0;
                for (Employé employeCentre : employés) {
                    int total =0;
                    boolean[] dispoEmp = new boolean[86400];

                    int heureDebut;
                    int heureFin;
                    int heureTotal;
                    boolean edtOk = false;
                    boolean HeureADJOk = false;
                    boolean HeureSemaineOk = false;
                    int countADJ = 0;
                    int countSemaine = 0;


                    switch (mission.get(j).getJour()) {
                        case "1":
                            dispoEmp = employeCentre.getEmployéEdt().getDispo1();
                            break;
                        case "2":
                            dispoEmp = employeCentre.getEmployéEdt().getDispo2();
                            break;
                        case "3":
                            dispoEmp = employeCentre.getEmployéEdt().getDispo3();
                            break;
                        case "4":
                            dispoEmp = employeCentre.getEmployéEdt().getDispo4();
                            break;
                        case "5":
                            dispoEmp = employeCentre.getEmployéEdt().getDispo5();
                            break;
                        default:
                            System.out.println("error jour semaine n'existe pas");
                            break;
                    }

                    //TODO rajouter pour le trajet avec la distance matrice recup un nmbre dans la matrice
                    //TODO rajouter pour le temps entre mission < 13h

                    heureDebut = Integer.parseInt(mission.get(j).getHeure_debut());
                    heureFin = Integer.parseInt(mission.get(j).getHeure_fin());
                    heureTotal = heureFin - heureDebut;

                    // Voir si employé dispo sur le temps de la mission
                    for ( i = 0; i< dispoEmp.length; i++) {
                        if (i>= heureDebut && i <= heureFin){
                            if(dispoEmp[i] == false ){
                                edtOk = true;

                            }else{
                                edtOk = false;
                            }
                        }

                    }
                    System.out.println("Employé dispo? " + edtOk);

                    // Voir si employé n'a pas trop travaillé adj si on rajoute cette mission
                    for (boolean value : dispoEmp) {
                        if (value == true) {
                            countADJ++;
                        }
                    }

                    countADJ += heureTotal;  // heure suposé en plus si il effectue la mission

                    if (countADJ <= 25200){ // < 7h
                        HeureADJOk = true;
                    }else{
                        HeureADJOk = false;
                    }
                    System.out.println("Employé heure <7h adj? " + HeureADJOk);

//                    System.out.println("Heure travaillées adj : " + countADJ);


                    // Voir si employé n'a pas trop travaillé adj si on rajoute cette mission
                    for (boolean value : employeCentre.getEmployéEdt().getDispo1()) {
                        if (value == true) {
                            countSemaine++;
                        }
                    }
                    for (boolean value : employeCentre.getEmployéEdt().getDispo2()) {
                        if (value == true) {
                            countSemaine++;
                        }
                    }
                    for (boolean value : employeCentre.getEmployéEdt().getDispo3()) {
                        if (value == true) {
                            countSemaine++;
                        }
                    }
                    for (boolean value : employeCentre.getEmployéEdt().getDispo4()) {
                        if (value == true) {
                            countSemaine++;
                        }
                    }
                    for (boolean value : employeCentre.getEmployéEdt().getDispo5()) {
                        if (value == true) {
                            countSemaine++;
                        }
                    }
                    countSemaine += heureTotal;  // heure suposé en plus si il effectue la mission

                    if (countSemaine <= 126000){  // < 35 heures
                        HeureSemaineOk = true;
                    }else{
                        HeureSemaineOk = false;
                    }
                    System.out.println("Employé heure <35h semaine? " + HeureSemaineOk);



                    //Verifs finales
                    System.out.println("Employé comp? " + employeCentre.getCompétence());
                    System.out.println("Mission comp? " + mission.get(j).getCompétence());
                    if (employeCentre.getCompétence().equals(mission.get(j).getCompétence())) {
                        total +=10;
                    }
                    if (edtOk == true && HeureADJOk == true && HeureSemaineOk == true){
                        total +=5;
                    }
                    if (employeCentre.getSpé().equals(mission.get(j).getSpé())){
                        total += 1;
                    }


                    // On donne la meilleure correspondance
                    if(total>totalBest){
                        bestEmploye = employeCentre;
                        totalBest = total;
                    }

                }

                System.out.println("Meilleur employé pour la mission " + mission.get(j).getId() + " : Employé " + bestEmploye.getId());
                System.out.println("Total Pts Fitness : " + totalBest);

                affectationsMissionAEmployeEtCentre(bestEmploye, mission.get(j));



            }




        }
    }


    public void affectationsMissionAEmployeEtCentre(Employé employe, Mission mission){
        boolean[] dispoEmp = new boolean[86400];

        switch (mission.getJour()) {
            case "1":
                dispoEmp = employe.getEmployéEdt().getDispo1();
                break;
            case "2":
                dispoEmp = employe.getEmployéEdt().getDispo2();
                break;
            case "3":
                dispoEmp = employe.getEmployéEdt().getDispo3();
                break;
            case "4":
                dispoEmp = employe.getEmployéEdt().getDispo4();
                break;
            case "5":
                dispoEmp = employe.getEmployéEdt().getDispo5();
                break;
            default:
                System.out.println("error jour semaine n'existe pas");
                break;
        }

        for(int i = Integer.parseInt(mission.getHeure_debut()); i < Integer.parseInt(mission.getHeure_fin()); i++){
            employe.getEmployéEdt().setElement(i-1, true, dispoEmp);
        }


        employe.addMissionAffectee(employe.getAffectation(), mission);

        for(Centre centre : centres){
            if(centre.getId() == employe.getCentreID()){
                centre.addMissionAffectee(employe.getAffectation(), mission);
            }

        }

        //TODO Ajouter les distances effectué aux employées depuis la matrice


    }


}
