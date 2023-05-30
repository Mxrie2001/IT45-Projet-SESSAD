public class Mission {
    private int id;
    private String jour;
    private String heure_debut;
    private String heure_fin;
    private String compétence;
    private String spé;

    public Mission(int id, String jour, String heure_debut, String heure_fin, String compétence, String spé) {
        this.id = id;
        this.jour = jour;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.compétence = compétence;
        this.spé = spé;
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

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", jour='" + jour + '\'' +
                ", heure_debut='" + heure_debut + '\'' +
                ", heure_fin='" + heure_fin + '\'' +
                ", compétence='" + compétence + '\'' +
                ", spé='" + spé + '\'' +
                '}';
    }

}
