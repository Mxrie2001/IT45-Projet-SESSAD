public class Mission {

    private int id;                // Identifiant unique de la mission
    private String jour;           // Jour de la mission
    private String heure_debut;    // Heure de début de la mission
    private String heure_fin;      // Heure de fin de la mission
    private String compétence;     // Compétence requise pour la mission (langage)
    private String spé;            // Spécialisation pour la mission
    private boolean isAffected;    // boolean indiquant si la mission est affectée à un employé

    public Mission(int id, String jour, String heure_debut, String heure_fin, String compétence, String spé) {
        this.id = id;
        this.jour = jour;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.compétence = compétence;
        this.spé = spé;
        this.isAffected=false;

    }

    public void setAffected(boolean affected) {
        isAffected = affected;
    }

    public boolean isAffected() {
        return isAffected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }
    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }
    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
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

    //retourne une chaine de caractère pour afficher les missions
    public String toStringMissions() {
        return "Mission{" +
                "id=" + id +
                ", jour='" + jour + '\'' +
                ", heure_debut='" + heure_debut + '\'' +
                ", heure_fin='" + heure_fin + '\'' +
                ", compétence='" + compétence + '\'' +
                ", spé='" + spé + '\'' +
                '}';
    }

    // fonction qui vérifie si deux missions ont lieu le même jour
    public boolean VerifJour(Mission mission1, Mission mission2){
        if(mission1.getJour().equals(mission2.getJour())){
            return true;
        }else{
            return false;
        }
    }

}
