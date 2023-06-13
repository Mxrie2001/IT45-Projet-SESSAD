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



    public void affectationM1J() {
        for (int jour = 1; jour < 6; jour++) {
            for (int i = 1; i < this.nbClusters + 1; i++) {
                Map<Mission, Double> closestMissions = kmeansmission.findClosestMissionsToCentreJ(i, String.valueOf(jour));
                List<Employé> employés = this.employésParCentres.get(i - 1);
                System.out.println("****************************************************************");
                System.out.println("Missions les plus proches du centre " + i + " au jour:" + jour);
                System.out.println("****************************************************************");

                for (Map.Entry<Mission, Double> entry : closestMissions.entrySet()) {
                    Mission mission = entry.getKey();
                    double distance = entry.getValue();
                    System.out.println("Mission n°" + mission.getId() + " - Distance : " + distance);

                    Employé bestEmploye = null;
                    int totalBest = 0;

                    for (Employé employeCentre : employés) {
                        int total = 0;

                        if (employeCentre.getCompétence().equals(mission.getCompétence())) {
                            total += 10;
                        }

                        if (employeCentre.getSpé().equals(mission.getSpé())) {
                            total += 1;
                        }

                        // On donne la meilleure correspondance
                        if (total > totalBest && total >= 10) {
                            bestEmploye = employeCentre;
                            totalBest = total;
                        }


                    }
                    System.out.println("Meilleur employé pour la mission " + mission.getId() + " : Employé " + bestEmploye.getId());
                    System.out.println("Total Pts Fitness : " + totalBest);
                }

            }
        }
    }

    public void affectationEmployes() {
        // Initialize Tabu List
        Set<Pair<Employé, Mission>> tabuList = new HashSet<>();

        // Affectation des missions aux employés pour chaque cluster
        for (int i = 0; i < this.nbClusters; i++) {
            List<Mission> mission = this.missions.get(i);
            List<Employé> employés = this.employésParCentres.get(i);

            System.out.println("*******************Cluster*********************");

            for (int j = 0; j < mission.size(); j++) {
                Employé bestEmploye = null;
                int totalBest = 0;
                double dist = 0.0;
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
//                    System.out.println("Employé dispo? " + edtOk);

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
//                    System.out.println("Employé heure <7h adj? " + HeureADJOk);

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
//                    System.out.println("Employé heure <35h semaine? " + HeureSemaineOk);



                    //Verifs finales
//                    System.out.println("Employé comp? " + employeCentre.getCompétence());
//                    System.out.println("Mission comp? " + mission.get(j).getCompétence());
                    if (employeCentre.getCompétence().equals(mission.get(j).getCompétence())) {
                        total +=10;
                    }
                    if (edtOk == true && HeureADJOk == true && HeureSemaineOk == true){
                        total +=5;
                    }
                    if (employeCentre.getSpé().equals(mission.get(j).getSpé())){
                        total += 1;
                    }

                    if (employeCentre.getLastMissionAffectee() != null && kmeansmission.findClosestMissions2(employeCentre.getLastMissionAffectee()) == mission.get(j)){
                        System.out.println("mission n°"+mission.get(j).getId() + " mission kmeans " + employeCentre.getLastMissionAffectee().getId());
                        total += 2;
                    }

                        // Check if the (employeCentre, mission) pair is in the Tabu List
                        Pair<Employé, Mission> currentPair = new Pair<>(employeCentre, mission.get(j), total, dist);
                        if (!tabuList.contains(currentPair)) {
                            // Update the bestEmploye and totalBest if the fitness is improved
                            if (total > totalBest && total >= 15) {
                                bestEmploye = employeCentre;
                                totalBest = total;
                            }
                        }
                    }

                    Pair<Employé, Mission> bestPair = new Pair<>(bestEmploye, mission.get(j), totalBest, dist); // a modifier dist
                    tabuList.add(bestPair);

                    if (tabuList.size() > Integer.MAX_VALUE) {
                        tabuList.remove(0);
                    }


                System.out.println("Meilleur employé pour la mission " + mission.get(j).getId() + " : Employé " + bestEmploye.getId());
                System.out.println("Total Pts Fitness : " + totalBest);

                affectationsMissionAEmployeEtCentre(bestEmploye, mission.get(j));
            }
        }

        for (Pair<Employé, Mission> pair : tabuList) {
            System.out.println("Pair: Employé " + pair.getEmploye().getId() + ", Mission " + pair.getMission().getId() + ", Fitness " + pair.getFitness());
        }
    }


//    public void affectationEmployes(){
//        // Affectation des missions aux employés pour chaque cluster
//        for (int i = 0; i < this.nbClusters; i++) {
//            List<Mission> mission = this.missions.get(i);
//            List<Employé> employés = this.employésParCentres.get(i);
//
//            for (int j = 0; j < mission.size(); j++) {
//                Employé bestEmploye = null;
//                int totalBest = 0;
//                for (Employé employeCentre : employés) {
//                    int total =0;
//                    boolean[] dispoEmp = new boolean[86400];
//
//                    int heureDebut;
//                    int heureFin;
//                    int heureTotal;
//                    boolean edtOk = false;
//                    boolean HeureADJOk = false;
//                    boolean HeureSemaineOk = false;
//                    int countADJ = 0;
//                    int countSemaine = 0;
//
//
//                    switch (mission.get(j).getJour()) {
//                        case "1":
//                            dispoEmp = employeCentre.getEmployéEdt().getDispo1();
//                            break;
//                        case "2":
//                            dispoEmp = employeCentre.getEmployéEdt().getDispo2();
//                            break;
//                        case "3":
//                            dispoEmp = employeCentre.getEmployéEdt().getDispo3();
//                            break;
//                        case "4":
//                            dispoEmp = employeCentre.getEmployéEdt().getDispo4();
//                            break;
//                        case "5":
//                            dispoEmp = employeCentre.getEmployéEdt().getDispo5();
//                            break;
//                        default:
//                            System.out.println("error jour semaine n'existe pas");
//                            break;
//                    }
//
//                    //TODO rajouter pour le trajet avec la distance matrice recup un nmbre dans la matrice
//                    //TODO rajouter pour le temps entre mission < 13h
//
//                    heureDebut = Integer.parseInt(mission.get(j).getHeure_debut());
//                    heureFin = Integer.parseInt(mission.get(j).getHeure_fin());
//                    heureTotal = heureFin - heureDebut;
//
//                    // Voir si employé dispo sur le temps de la mission
//                    for ( i = 0; i< dispoEmp.length; i++) {
//                        if (i>= heureDebut && i <= heureFin){
//                            if(dispoEmp[i] == false ){
//                                edtOk = true;
//
//                            }else{
//                                edtOk = false;
//                            }
//                        }
//
//                    }
////                    System.out.println("Employé dispo? " + edtOk);
//
//                    // Voir si employé n'a pas trop travaillé adj si on rajoute cette mission
//                    for (boolean value : dispoEmp) {
//                        if (value == true) {
//                            countADJ++;
//                        }
//                    }
//
//                    countADJ += heureTotal;  // heure suposé en plus si il effectue la mission
//
//                    if (countADJ <= 25200){ // < 7h
//                        HeureADJOk = true;
//                    }else{
//                        HeureADJOk = false;
//                    }
////                    System.out.println("Employé heure <7h adj? " + HeureADJOk);
//
////                    System.out.println("Heure travaillées adj : " + countADJ);
//
//
//                    // Voir si employé n'a pas trop travaillé adj si on rajoute cette mission
//                    for (boolean value : employeCentre.getEmployéEdt().getDispo1()) {
//                        if (value == true) {
//                            countSemaine++;
//                        }
//                    }
//                    for (boolean value : employeCentre.getEmployéEdt().getDispo2()) {
//                        if (value == true) {
//                            countSemaine++;
//                        }
//                    }
//                    for (boolean value : employeCentre.getEmployéEdt().getDispo3()) {
//                        if (value == true) {
//                            countSemaine++;
//                        }
//                    }
//                    for (boolean value : employeCentre.getEmployéEdt().getDispo4()) {
//                        if (value == true) {
//                            countSemaine++;
//                        }
//                    }
//                    for (boolean value : employeCentre.getEmployéEdt().getDispo5()) {
//                        if (value == true) {
//                            countSemaine++;
//                        }
//                    }
//                    countSemaine += heureTotal;  // heure suposé en plus si il effectue la mission
//
//                    if (countSemaine <= 126000){  // < 35 heures
//                        HeureSemaineOk = true;
//                    }else{
//                        HeureSemaineOk = false;
//                    }
////                    System.out.println("Employé heure <35h semaine? " + HeureSemaineOk);
//
//
//
//                    //Verifs finales
////                    System.out.println("Employé comp? " + employeCentre.getCompétence());
////                    System.out.println("Mission comp? " + mission.get(j).getCompétence());
//                    if (employeCentre.getCompétence().equals(mission.get(j).getCompétence())) {
//                        total +=10;
//                    }
//                    if (edtOk == true && HeureADJOk == true && HeureSemaineOk == true){
//                        total +=5;
//                    }
//                    if (employeCentre.getSpé().equals(mission.get(j).getSpé())){
//                        total += 1;
//                    }
//
////                    if (employeCentre.getLastMissionAffectee() != null && kmeansmission.findClosestMissions2(employeCentre.getLastMissionAffectee()) == mission){
////                        total += 2;
////                    }
//
//
//                    // On donne la meilleure correspondance
//                    if(total>totalBest && total>=15){
//                        bestEmploye = employeCentre;
//                        totalBest = total;
//                    }
//
//                }
//
//                System.out.println("Meilleur employé pour la mission " + mission.get(j).getId() + " : Employé " + bestEmploye.getId());
//                System.out.println("Total Pts Fitness : " + totalBest);
//
//                affectationsMissionAEmployeEtCentre(bestEmploye, mission.get(j));
//
//
//
//            }
//
//        }
//    }


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

    public void utiliserAlgoTabou(List<List<List<Mission>>> listeCompatibilite) {
        // Paramètres de l'algorithme tabou
        int tailleTabou = 5; // Taille de la liste tabou
        int maxIterations = 20; // Nombre maximum d'itérations

        // Meilleurs chemins trouvés et distance minimale
        List<List<List<Mission>>> meilleursChemins = new ArrayList<>();
        double distanceMin = Double.MAX_VALUE;

        // Boucle pour chaque employé
        for (List<List<Mission>> missionsEmploye : listeCompatibilite) {
            // Chemin courant initial avec les missions réparties par jour pour l'employé
            List<List<Mission>> cheminCourant = new ArrayList<>(missionsEmploye);

            // Variables tabou
            Deque<List<List<Mission>>> listeTabou = new LinkedList<>();
            listeTabou.add(cheminCourant);

            // Boucle principale de l'algorithme
            int iteration = 0;
            while (iteration < maxIterations) {
                // Générer une nouvelle permutation voisine du chemin courant
                List<List<Mission>> voisin = genererVoisin(cheminCourant);

                // Vérifier si le voisin est dans la liste tabou
                if (!listeTabou.contains(voisin)) {
                    // Calculer la distance du voisin
                    double distanceVoisin = calculerDistance(voisin);

                    // Mettre à jour les meilleurs chemins et la distance minimale si nécessaire
                    if (distanceVoisin < distanceMin) {
                        meilleursChemins.clear();
                        meilleursChemins.add(new ArrayList<>(voisin));
                        distanceMin = distanceVoisin;
                    }

                    // Mettre à jour le chemin courant avec le voisin
                    cheminCourant = new ArrayList<>(voisin);

                    // Ajouter le voisin à la liste tabou
                    listeTabou.add(voisin);

                    // Supprimer l'élément le plus ancien de la liste tabou si elle dépasse la taille tabou
                    if (listeTabou.size() > tailleTabou) {
                        listeTabou.removeFirst();
                    }

                    // Réinitialiser le compteur d'itérations
                    iteration = 0;
                }

                // Incrémenter le compteur d'itérations
                iteration++;
            }
        }

        // Afficher les meilleurs chemins et leur distance totale
        System.out.println("Meilleurs chemins :");
        for (List<List<Mission>> chemin : meilleursChemins) {
            System.out.println("Chemin :");
            for (List<Mission> jour : chemin) {
                for (Mission mission : jour) {
                    System.out.println("Mission : " + mission.getId());
                }
            }
        }
        System.out.println("Distance totale : " + distanceMin);
    }

    // Méthode pour générer un voisin à partir d'une permutation donnée
    private List<List<Mission>> genererVoisin(List<List<Mission>> permutation) {
        List<List<Mission>> voisin = new ArrayList<>(permutation);

        // Sélectionner aléatoirement deux positions dans la permutation
        int position1 = new Random().nextInt(voisin.size());
        int position2 = new Random().nextInt(voisin.size());

        // Permuter les missions aux deux positions sélectionnées
        List<Mission> jour1 = voisin.get(position1);
        List<Mission> jour2 = voisin.get(position2);
        voisin.set(position1, jour2);
        voisin.set(position2, jour1);

        return voisin;
    }

    // Méthode pour calculer la distance totale d'un chemin donné
    private double calculerDistance(List<List<Mission>> chemin) {
        double distanceTotale = 0.0;

        // Parcourir le chemin et calculer la distance entre chaque paire de missions consécutives
        for (List<Mission> jour : chemin) {
            for (int i = 0; i < jour.size() - 1; i++) {
                Mission missionCourante = jour.get(i);
                Mission missionSuivante = jour.get(i + 1);
                // Utiliser votre matrice de distances pour obtenir la distance entre les missions
                double distance = distances[missionCourante.getId()][missionSuivante.getId()];
                distanceTotale += distance;
            }
        }

        return distanceTotale;
    }



    private List<Mission> genererPermutationAleatoire(List<List<List<Mission>>> listeCompatibilite) {
        List<Mission> permutation = new ArrayList<>();

        // Parcourir la liste de compatibilité et sélectionner aléatoirement une mission par jour
        for (List<List<Mission>> jourCompatibilite : listeCompatibilite) {
            for (List<Mission> missionCompatibilite : jourCompatibilite) {
                if (!missionCompatibilite.isEmpty()) {
                    Mission missionAleatoire = missionCompatibilite.get(new Random().nextInt(missionCompatibilite.size()));
                    permutation.add(missionAleatoire);
                    break;
                }
            }
        }

        return permutation;
    }

}
