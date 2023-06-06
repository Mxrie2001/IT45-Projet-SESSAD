public class EmployéEdt {
    private boolean[] dispo1;
    private boolean[] dispo2;
    private boolean[] dispo3;
    private boolean[] dispo4;
    private boolean[] dispo5;

    public EmployéEdt() {
        this.dispo1 =  new boolean[24];
        this.dispo2 = new boolean[24];
        this.dispo3 = new boolean[24];
        this.dispo4 = new boolean[24];
        this.dispo5 = new boolean[24];
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

    public void AfficherTab(boolean[] dispo){
        for (boolean element : dispo) {
            System.out.print(element + " ");
        }
    }


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

