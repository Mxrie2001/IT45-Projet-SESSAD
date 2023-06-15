public class EmployéEdt {

    //Différents tableaux pou chaque jour de la semaine
    private boolean[] dispo1;
    private boolean[] dispo2;
    private boolean[] dispo3;
    private boolean[] dispo4;
    private boolean[] dispo5;

    public EmployéEdt() {
        this.dispo1 =  new boolean[86400];
        this.dispo2 = new boolean[86400];
        this.dispo3 = new boolean[86400];
        this.dispo4 = new boolean[86400];
        this.dispo5 = new boolean[86400];
    }

    public boolean[] getDispo1() {
        return dispo1;
    }

    public void setDispo1(boolean[] dispo1) {
        this.dispo1 = dispo1;
    }

    public boolean[] getDispo2() {
        return dispo2;
    }

    public void setDispo2(boolean[] dispo2) {
        this.dispo2 = dispo2;
    }

    public boolean[] getDispo3() {
        return dispo3;
    }

    public void setDispo3(boolean[] dispo3) {
        this.dispo3 = dispo3;
    }

    public boolean[] getDispo4() {
        return dispo4;
    }

    public void setDispo4(boolean[] dispo4) {
        this.dispo4 = dispo4;
    }

    public boolean[] getDispo5() {
        return dispo5;
    }

    public void setDispo5(boolean[] dispo5) {
        this.dispo5 = dispo5;
    }

    // Fonction pour afficher les différents tableaux pour voir les disponibilités des employés
    public void AfficherTab(boolean[] dispo){
        for (boolean element : dispo) {
            System.out.print(element + " ");
        }
    }

    public void setElement(int index, boolean value, boolean[] tab) {
        if (index >= 0 && index < tab.length) {
            tab[index] = value;
        } else {
            System.out.println("Indice hors limites");
        }
    }

    // Retourne une chaine de caractère qui affiche tous les différents tableaux qui nous permettent de voir les disponibilités des employés
    public String toStringEmployeEDT() {
        return "EmployeEdt{" +
                "dispo1=" + dispo1 +
                ", dispo2=" + dispo2 +
                ", dispo3=" + dispo3 +
                ", dispo4=" + dispo4 +
                ", dispo5=" + dispo5 +
                '}';
    }
}

