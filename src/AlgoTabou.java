import java.beans.beancontext.BeanContext;
import java.util.*;
import java.util.Map.Entry;

public class AlgoTabou {
    private List<List<Mission>> missions; //Liste des liste de missions réparties par cluster

    private List<List<Employé>> employésParCentres; // Liste des listes d'employés répartis par clusters
    private List<Employé> employes; //Liste de tous les employés
    private List<Centre> centres; // Liste des centres

    private int nbClusters; // Nombre de cluster

    private Kmeans kmeansmission; //Instance de kmeans pour avoir acces aux fonctions

    double[][] distances; // Matrice des distance

    private int coutTotalAlgo = 0; // Cout total

    private List<Mission> allMissions; // Liste de toute les missions

    //Constructeur
    public AlgoTabou(List<List<Mission>> missions, double[][] distances, List<Employé> employes, List<Centre> centres, int nbClusters, Kmeans kmeansmission, List<Mission> allMission) {
        this.missions = missions;
        this.employes = employes;
        this.distances=distances;
        this.centres = centres;
        this.nbClusters = nbClusters;
        this.kmeansmission = kmeansmission;
        this.employésParCentres = new ArrayList<>();
        this.allMissions = allMission;
    }

    public String toStringAlgoTabou() {
        return "AlgoTabou{" +
                "Liste Missions =" + this.missions +
                ",Liste Employés='" + this.employes +
                ",Liste Centres='" + this.centres +
                ",Nbre de cluster='" + this.nbClusters +
                '}';
    }



    // Fonction principale de Tabou, a partir des couples compatible créé je selectionne les meilleurs et les affecte
    //Comparaison 1 à 1 avec son voisin
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
                        // supprimer l'affectation au moins bon si elle lui était affectée
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
                        // supprimer l'affectation au moins bon si elle lui était affectée
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

        return couples;
    }


    //Fonction de calcul du score/fitnass des employés par rapport à une mission
    //Pris en compte de tout les contrainte sauf le temps de trajet entre les missions
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
        countSemaine += heureTotal;  // heure supposé en plus si il effectue la mission

        if (countSemaine <= 126000){  // < 35 heures
            HeureSemaineOk = true;
        }else{
            HeureSemaineOk = false;
        }

        //Verifs finales
        if (employé.getCompétence().equals(mission.getCompétence())) {
            total +=10; // dejà verifié mais au cas où
        }
        if (edtOk == true && HeureADJOk == true && HeureSemaineOk == true){
            total +=6; // contrainte emploi du temps
        }
        if (employé.getSpé().equals(mission.getSpé())){
            total += 1; // contrainte spécialité
        }

        // temps entre mission inférieur ou égale à 13h
        if (employé.getLastMissionAffectee() != null && heureDebut - Integer.parseInt(employé.getLastMissionAffectee().getHeure_fin()) <= 46800){
            total += 5; // contrainte des 13h entre les mission
        }

        employé.setScore(total);
    }


    //fonction de mise à jour des emplois du temps employé que ce soit pour affecter ou enlever des missions
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


    // Fonction d'affectation d'une mission
    public void affecterMission(Employé employe, Mission mission){
        employe.addMissionAffectee(mission);
    }

    //Fonction de suppression d'affectation d'une mission
    public void removemissionAffectee(Employé employe, Mission mission){
        employe.removeMissionFromAffectation(mission);
    }

    //Fonction de verification si une mission est déjà affecté à un employé
    public boolean checkMissionInAffectation(Employé employe, Mission mission){
        return employe.isInAffectation(mission);
    }



    //Fonction d'affichage des chemins optimaux par employé par jours
    public void affichageCheminOptimaux() {
            for(Employé employe : employes){
                //remettre en ordre en fonction des horraires les missions affectées
                this.remettreOrdreMissions(employe);

                System.out.println("\nEmployé " + employe.getId() + " assigné au centre n°" + employe.getCentreID());
                List<Mission> affectation = employe.getAffectation();

                for(int jour = 1; jour<=5; jour++) {
                    double distanceOpti = 0.0;
                    System.out.println("Jour "+ jour);

                    List<Mission> missionsTemporaires = new ArrayList<>();

                    for (Mission mission : affectation) {
                        if (Integer.parseInt(mission.getJour()) == jour) {
                            missionsTemporaires.add(mission);
                        }
                    }

                    for (Mission mission : missionsTemporaires) {

                         if (this.estPremierElement(missionsTemporaires, mission) && this.estDernierElement(missionsTemporaires, mission)) {
                            distanceOpti += kmeansmission.findDistance("centre", "mission", employe.getCentreID(), mission.getId());
                            distanceOpti += kmeansmission.findDistance("mission", "centre", mission.getId(), employe.getCentreID());
                        }else{
                             if(this.estPremierElement(missionsTemporaires, mission)){
                                 distanceOpti += kmeansmission.findDistance("centre", "mission", employe.getCentreID(), mission.getId());
                             } else if (this.estDernierElement(missionsTemporaires, mission)) {
                                 Mission missionPrec = this.getElementPrecedent(missionsTemporaires, mission);
                                 distanceOpti += kmeansmission.findDistance("mission", "mission", missionPrec.getId(), mission.getId());
                                 distanceOpti += kmeansmission.findDistance("mission", "centre", mission.getId(), employe.getCentreID());
                             }else{
                                 Mission missionPrec = this.getElementPrecedent(missionsTemporaires, mission);
                                 distanceOpti += kmeansmission.findDistance("mission", "mission", missionPrec.getId(), mission.getId());
                             }
                         }

                        System.out.print("-" + mission.getId() + "-" );
                    }
                    System.out.println(" => distance opti = " + distanceOpti);
                    //maj distance dans employe
                    this.majDistancePouremploye(employe, jour, distanceOpti);
                }
                // calculer le total de distance sur la semaine pour l'employé
                employe.calculateDistanceTotal();

                System.out.println("distance totale optimale semaine pour employé n°"+ employe.getId() + " = " + employe.getDistanceTotal());
            }
            //Affectation des missions aux centres + calcul distance totale et cout
            affectationMissionCentre();
            for (Centre centre : centres){
                this.coutTotalAlgo += centre.getCoutTotalCentre();
            }

            System.out.println("\nLe coût des distance c =" + this.coutTotalAlgo + "€\n");


    }


    //Verification si une mission est la premiere affectée a un employé (sur 1 jour) pour le calcul des distance
    public boolean estPremierElement(List<Mission> liste, Mission element){
        return liste.get(0).equals(element);
    }

    //Verification si une mission est la derniere affectée a un employé (sur 1 jour) pour le calcul des distance
    public boolean estDernierElement(List<Mission> liste, Mission element){
        return liste.get(liste.size() - 1).equals(element);
    }

    //Fonction pour prendre la mission precedente (sur 1 jour), toujour pour le calcul des distance
    public Mission getElementPrecedent(List<Mission> liste, Mission element){
        int indexCourant = liste.indexOf(element);
        Mission elementPrecedent = liste.get(indexCourant - 1);

        return elementPrecedent;
    }

    // Fonction de mise à jour de la distance effectué par un employé
    public void majDistancePouremploye(Employé e, int jour, double distOpti){
        switch (jour) {
            case 1:
                e.setDistanceJ1(distOpti);
                break;
            case 2:
                e.setDistanceJ2(distOpti);
                break;
            case 3:
                e.setDistanceJ3(distOpti);
                break;
            case 4:
                e.setDistanceJ4(distOpti);
                break;
            case 5:
                e.setDistanceJ5(distOpti);
                break;
            default:
                System.out.println("error jour semaine n'existe pas");
                break;
        }
    }


    // Fonction qui tri les mission par horraire (appelé pour 1 jour a chaque fois) pour encore améliorer le chemin optimal
    public List<Mission> triMissionAffecteParJour(List<Mission> missions) {
        Collections.sort(missions, Comparator.comparingInt(mission -> Integer.parseInt(mission.getHeure_debut())));
        return missions;
    }

    //Fonction de remise en ordre des mission, trié par jour et horraire avant calcul des distance
    public void remettreOrdreMissions(Employé employe){
        List<Mission> newAffectation = new ArrayList<>();

        List<Mission> newAffectationJ1 = new ArrayList<>();
        List<Mission> newAffectationJ2 = new ArrayList<>();
        List<Mission> newAffectationJ3 = new ArrayList<>();
        List<Mission> newAffectationJ4 = new ArrayList<>();
        List<Mission> newAffectationJ5 = new ArrayList<>();

        List<Mission> affectation = employe.getAffectation();
        for(int jour = 1; jour<=5; jour++) {
            List<Mission> missionsTemporaires = new ArrayList<>();

            for (Mission mission : affectation) {
                if (Integer.parseInt(mission.getJour()) == jour) {
                    missionsTemporaires.add(mission);
                }
            }
            switch (jour) {
                case 1:
                    newAffectationJ1 =missionsTemporaires;
                break;
                case 2:
                    newAffectationJ2 = missionsTemporaires;
                    break;
                case 3:
                    newAffectationJ3 = missionsTemporaires;
                    break;
                case 4:
                    newAffectationJ4 = missionsTemporaires;
                    break;
                case 5:
                    newAffectationJ5 = missionsTemporaires;
                    break;
                default:
                    System.out.println("error jour semaine n'existe pas");
                    break;
            }

        }
        //trier toute les affectation par jour en fonction de leur heure de debut
        List<Mission> tri1 = triMissionAffecteParJour(newAffectationJ1);
        List<Mission> tri2 = triMissionAffecteParJour(newAffectationJ2);
        List<Mission> tri3 =triMissionAffecteParJour(newAffectationJ3);
        List<Mission> tri4 = triMissionAffecteParJour(newAffectationJ4);
        List<Mission> tri5 = triMissionAffecteParJour(newAffectationJ5);

        newAffectation.addAll(tri1);
        newAffectation.addAll(tri2);
        newAffectation.addAll(tri3);
        newAffectation.addAll(tri4);
        newAffectation.addAll(tri5);

        employe.setAffectation(newAffectation);
    }



    //Fonction d'affectation des missions aux centres des mission affecté à ses employé associé
    public void affectationMissionCentre(){
        for (Centre centre : centres){
            List<Mission> listeTempAffectations = new ArrayList<>();
            double distTotale = 0.0;
            double cout = 0.0;
            for(Employé employe : employes){
                if(employe.getCentreID() == centre.getId()){
                    listeTempAffectations.addAll(employe.getAffectation());
                    distTotale += employe.getDistanceTotal();
                }
            }
            cout = distTotale*0.2;
            centre.setAffectation(listeTempAffectations);
            centre.setDistanceTrajets(distTotale);
            centre.setCoutTotalCentre(cout);
        }

    }



    // Fonction de vérification, toutes les missions sont affectées?
    public boolean verifAllMissionaffected(){

        List<Mission> allMissionAffected = new ArrayList<>();
        for(Employé employe : employes){
            allMissionAffected.addAll(employe.getAffectation());
        }

        return allMissionAffected.containsAll(this.allMissions);
    }


    // Fonction de retout des mission non affectées si c'est le cas
    public List<Mission> RetourneMissionPasAffectee(){

        List<Mission> missionPasAffected = new ArrayList<>();

        List<Mission> allMissionAffected = new ArrayList<>();

        for(Employé employe : employes){
            allMissionAffected.addAll(employe.getAffectation());
        }

        for (Mission mission : allMissions) {
            if (!allMissionAffected.contains(mission)) {
                missionPasAffected.add(mission);
            }
        }

        return missionPasAffected;
    }


    // Affectation des mission non affecté dans un premier temps, suivant de nouveau le processus de fitness
    public void affecterMissionPasAffectees(List<Mission> missionPasAffectees){

        for(Mission mission : missionPasAffectees){
            int bestScore = 0;
            double minDistance = 99999;
            Employé bestEmploye = null;

            for(Employé em : employes){
                this.majScoreEmploye(em, mission);
                int score = em.getScore();
                if(score >= 16 && score >=  bestScore) {
                    bestEmploye = em;
                    bestScore = score;
                }
            }

            this.affecterMission(bestEmploye, mission);
            this.majEDTEmploye(bestEmploye, Integer.parseInt(mission.getJour()), Integer.parseInt(mission.getHeure_debut()), Integer.parseInt(mission.getHeure_fin()), true);

        }

    }

    // rechercher les dernier doublons, s'il y en a
    public List<Mission> AllMissionaffectedDoublons(){
        List<Mission> missionDoubles = new ArrayList<>();
        List<Mission> allMissionAffected = new ArrayList<>();

        for(Employé employe : employes){
            allMissionAffected.addAll(employe.getAffectation());
        }

        for (int i = 0; i < allMissionAffected.size(); i++) {
            Mission m = allMissionAffected.get(i);
            for (int j = i + 1; j < allMissionAffected.size(); j++) {
                if (m.equals(allMissionAffected.get(j)) && !missionDoubles.contains(m)) {
                    missionDoubles.add(m);
                    break;
                }
            }
        }
        return missionDoubles;
    }


    // Derniere suppression des doublons si ils sont présents, de nouveau avec la selection du meilleur employé compatible
    public void suppressionDoublonApresAffectation(List<Mission> missionDouble){

        for(Mission mission : missionDouble){

            List<Employé> employeQuiAMissionDoubles = new ArrayList<>();

            for(Employé e : employes){
                if(e.getAffectation().contains(mission)){
                    employeQuiAMissionDoubles.add(e);
                }
            }
            int bestScore = 0;
            double minDistance = 99999;
            Employé bestEmploye = null;
            for(Employé em : employeQuiAMissionDoubles){
                int score = 0;
                if (em.getSpé().equals(mission.getSpé())){
                    score += 1;
                }
                if(score > bestScore){
                    bestEmploye = em;
                    bestScore = score;
                }
            }
            if(bestScore == 0){
                for(Employé em : employeQuiAMissionDoubles){
                    int score = 0;
                    double dist = 0.0;
                    if(em.getLastMissionAffectee() != null){
                        dist = kmeansmission.findDistance("mission", "mission", em.getLastMissionAffectee().getId(), mission.getId());
                    }
                    if(em.getLastMissionAffectee() == null){
                        dist = kmeansmission.findDistance("centre", "mission", em.getCentreID(), mission.getId());
                    }

                    if(dist< minDistance){
                        score +=1;
                    }

                    if(score > bestScore){
                        bestEmploye = em;
                        bestScore = score;
                    }
                }
            }

            for(Employé employe : employeQuiAMissionDoubles) {
                if (employe != bestEmploye) {
                    this.removemissionAffectee(employe, mission);
                    this.majEDTEmploye(employe, Integer.parseInt(mission.getJour()), Integer.parseInt(mission.getHeure_debut()), Integer.parseInt(mission.getHeure_fin()), false);
                }
            }
        }
    }


    // vérification finale rapport compatibilité compétence et spécialité
    public void verifAlgoOK(){
        int verifokCompetences = 0;
        int verifokSpe = 0;
        int totalAVerif = allMissions.size();
        for (Employé employe : employes){
            for (Mission mission : employe.getAffectation()){
                if(mission.getCompétence().equals(employe.getCompétence())){
                    verifokCompetences +=1;
                }
                if(mission.getSpé().equals(employe.getSpé())){
                    verifokSpe +=1;
                }
            }
        }

        System.out.println("*********************************");
        System.out.println("Vérifications résultats");
        System.out.println("*********************************");
        System.out.println("Association (Employé,Mission) avec même compétence : " + verifokCompetences + "/" + totalAVerif);
        System.out.println("Association (Employé,Mission) avec même spé : " + verifokSpe + "/" + totalAVerif);
    }


}
