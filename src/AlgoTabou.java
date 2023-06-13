import java.beans.beancontext.BeanContext;
import java.util.*;
import java.util.Map.Entry;

public class AlgoTabou {
    private List<List<Mission>> missions;

    private List<List<Employé>> employésParCentres;
    private List<Employé> employes;
    private List<Centre> centres;

    private int nbClusters;

    private Kmeans kmeansmission;

    private int trajettotCentre1 = 0;
    private int trajettotCentre2 = 0;
    private int trajettotCentre3 = 0;
    double[][] distances;

    public AlgoTabou(List<List<Mission>> missions, double[][] distances, List<Employé> employes, List<Centre> centres, int nbClusters, Kmeans kmeansmission) {
        this.missions = missions;
        this.employes = employes;
        this.distances=distances;
        this.centres = centres;
        this.nbClusters = nbClusters;
        this.kmeansmission = kmeansmission;
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



    public List<CoupleEmployéMission> comparerEtSupprimerDoublonsMission(int centreIndex, int jour) {
        List<CoupleEmployéMission> couplesASupprimer = new ArrayList<>();
        List<CoupleEmployéMission> couples = kmeansmission.créerCouplesEmployéMission(centreIndex,jour); // attention index centre commence à 0 ne correspond pas à l'id du centre

        for (int i = 0; i < couples.size(); i++) {
            CoupleEmployéMission coupleCourant = couples.get(i);
            Mission missionCourante = coupleCourant.getMission();

            for (int j = i + 1; j < couples.size(); j++) {
                CoupleEmployéMission coupleSuivant = couples.get(j);
                Mission missionSuivante = coupleSuivant.getMission();

                if (missionCourante.equals(missionSuivante)) {

                    //maj des score
                    this.majScoreEmploye(coupleCourant.getEmployé(), missionCourante);
                    this.majScoreEmploye(coupleSuivant.getEmployé(), missionCourante);

                    double dCourant= 0.0;
                    double dSuivant= 0.0;

                    // gestion du parametre distance pour les score
                    if(coupleCourant.getEmployé().getLastMissionAffectee() != null){
                        dCourant = kmeansmission.findDistance("mission", "mission", coupleCourant.getEmployé().getLastMissionAffectee().getId(), missionCourante.getId());
                    }
                    if(coupleCourant.getEmployé().getLastMissionAffectee() == null){
                        dCourant = kmeansmission.findDistance("centre", "mission", coupleCourant.getEmployé().getCentreID(), missionCourante.getId());
                    }

                    if(coupleSuivant.getEmployé().getLastMissionAffectee() != null){
                        dSuivant = kmeansmission.findDistance("mission", "mission", coupleSuivant.getEmployé().getLastMissionAffectee().getId(), missionCourante.getId());
                    }
                    if(coupleSuivant.getEmployé().getLastMissionAffectee() == null){
                        dSuivant = kmeansmission.findDistance("centre", "mission", coupleSuivant.getEmployé().getCentreID(), missionCourante.getId());
                    }

                    if(dCourant > dSuivant){
                        coupleCourant.getEmployé().setScore(coupleCourant.getEmployé().getScore() + 10);
                    }
                    if(dCourant < dSuivant){
                        coupleSuivant.getEmployé().setScore(coupleSuivant.getEmployé().getScore() + 10);
                    }

                    if (coupleSuivant.getEmployé().getScore() > coupleCourant.getEmployé().getScore()) {

                        // affecter la bonne mission et maj edt
                        if(this.checkMissionInAffectation(coupleSuivant.getEmployé(), coupleSuivant.getMission()) == false){
                            this.affecterMission(coupleSuivant.getEmployé(), coupleSuivant.getMission());
                            this.majEDTEmploye(coupleSuivant.getEmployé(), jour, Integer.parseInt(coupleSuivant.getMission().getHeure_debut()), Integer.parseInt(coupleSuivant.getMission().getHeure_fin()), true);
                        }
                        if(this.checkMissionInAffectation(coupleCourant.getEmployé(), coupleCourant.getMission()) == true){
                            this.removemissionAffectee(coupleCourant.getEmployé(), coupleCourant.getMission());
                            this.majEDTEmploye(coupleCourant.getEmployé(), jour, Integer.parseInt(coupleCourant.getMission().getHeure_debut()), Integer.parseInt(coupleCourant.getMission().getHeure_fin()), false);
                        }

                        couplesASupprimer.add(coupleCourant);

                    } else {

                        // affecter la bonne mission et maj edt
                        if(this.checkMissionInAffectation(coupleCourant.getEmployé(), coupleCourant.getMission()) == false){
                            this.affecterMission(coupleCourant.getEmployé(), coupleCourant.getMission());
                            this.majEDTEmploye(coupleCourant.getEmployé(), jour, Integer.parseInt(coupleCourant.getMission().getHeure_debut()), Integer.parseInt(coupleCourant.getMission().getHeure_fin()), true);
                        }
                        if(this.checkMissionInAffectation(coupleSuivant.getEmployé(), coupleSuivant.getMission()) == true){
                            this.removemissionAffectee(coupleSuivant.getEmployé(), coupleSuivant.getMission());
                            this.majEDTEmploye(coupleSuivant.getEmployé(), jour, Integer.parseInt(coupleSuivant.getMission().getHeure_debut()), Integer.parseInt(coupleSuivant.getMission().getHeure_fin()), false);
                        }

                        couplesASupprimer.add(coupleSuivant);
                    }
                }
                coupleCourant.getEmployé().setScore(0);
                coupleSuivant.getEmployé().setScore(0);
            }
        }

        couples.removeAll(couplesASupprimer);

        System.out.println("Couples sans doublon :");
        for (CoupleEmployéMission couple : couples) {
            System.out.println("Employé n°" + couple.getEmployé().getId() + " - Mission n°" + couple.getMission().getId());
        }

        return couples;
    }


    public void majScoreEmploye(Employé employé, Mission mission){

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


        switch (mission.getJour()) {
            case "1":
                dispoEmp = employé.getEmployéEdt().getDispo1();
                break;
            case "2":
                dispoEmp = employé.getEmployéEdt().getDispo2();
                break;
            case "3":
                dispoEmp = employé.getEmployéEdt().getDispo3();
                break;
            case "4":
                dispoEmp = employé.getEmployéEdt().getDispo4();
                break;
            case "5":
                dispoEmp = employé.getEmployéEdt().getDispo5();
                break;
            default:
                System.out.println("error jour semaine n'existe pas");
                break;
        }

        heureDebut = Integer.parseInt(mission.getHeure_debut());
        heureFin = Integer.parseInt(mission.getHeure_fin());
        heureTotal = heureFin - heureDebut;

        // Voir si employé dispo sur le temps de la mission
        for (int i = 0; i< dispoEmp.length; i++) {
            if (i>= heureDebut && i <= heureFin){
                if(dispoEmp[i] == false ){
                    edtOk = true;

                }else{
                    edtOk = false;
                }
            }

        }
//      System.out.println("Employé dispo? " + edtOk);

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
//  System.out.println("Employé heure <7h adj? " + HeureADJOk);

//  System.out.println("Heure travaillées adj : " + countADJ);

        // Voir si employé n'a pas trop travaillé adj si on rajoute cette mission
        for (boolean value : employé.getEmployéEdt().getDispo1()) {
            if (value == true) {
                countSemaine++;
            }
        }
        for (boolean value : employé.getEmployéEdt().getDispo2()) {
            if (value == true) {
                countSemaine++;
            }
        }
        for (boolean value : employé.getEmployéEdt().getDispo3()) {
            if (value == true) {
                countSemaine++;
            }
        }
        for (boolean value : employé.getEmployéEdt().getDispo4()) {
            if (value == true) {
                countSemaine++;
            }
        }
        for (boolean value : employé.getEmployéEdt().getDispo5()) {
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
//  System.out.println("Employé heure <35h semaine? " + HeureSemaineOk);
        //Verifs finales
//  System.out.println("Employé comp? " + employeCentre.getCompétence());
//  System.out.println("Mission comp? " + mission.get(j).getCompétence());



        if (employé.getCompétence().equals(mission.getCompétence())) {
            total +=10; // dejà verifié mais au cas où
        }
        if (edtOk == true && HeureADJOk == true && HeureSemaineOk == true){
            total +=6;
        }
        if (employé.getSpé().equals(mission.getSpé())){
            total += 1;
        }

        // temps entre mission inférieur ou égale à 13h
        if (employé.getLastMissionAffectee() != null && heureDebut - Integer.parseInt(employé.getLastMissionAffectee().getHeure_fin()) <= 46800){
            total += 5;
        }

        employé.setScore(total);
    }

    public void majEDTEmploye(Employé employe, int jour, int heureDeb, int heureFin, boolean value){
        boolean[] dispoEmp = new boolean[86400];

        switch (jour) {
            case 1:
                dispoEmp = employe.getEmployéEdt().getDispo1();
                break;
            case 2:
                dispoEmp = employe.getEmployéEdt().getDispo2();
                break;
            case 3:
                dispoEmp = employe.getEmployéEdt().getDispo3();
                break;
            case 4:
                dispoEmp = employe.getEmployéEdt().getDispo4();
                break;
            case 5:
                dispoEmp = employe.getEmployéEdt().getDispo5();
                break;
            default:
                System.out.println("error jour semaine n'existe pas");
                break;
        }

        for(int i = heureDeb; i < heureFin; i++){
            employe.getEmployéEdt().setElement(i-1, value , dispoEmp);
        }
    }


    public void affecterMission(Employé employe, Mission mission){
        employe.addMissionAffectee(mission);
    }

    public void removemissionAffectee(Employé employe, Mission mission){
        employe.removeMissionFromAffectation(mission);
    }

    public boolean checkMissionInAffectation(Employé employe, Mission mission){
        return employe.isInAffectation(mission);
    }


}
