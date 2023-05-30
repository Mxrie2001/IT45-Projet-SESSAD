public class EmployéEdt {
    private boolean[] dispo1;
    private boolean[] dispo2;
    private boolean[] dispo3;
    private boolean[] dispo4;
    private boolean[] dispo5;

    public EmployéEdt(boolean[] dispo1, boolean[] dispo2, boolean[] dispo3, boolean[] dispo4, boolean[] dispo5) {
        this.dispo1 = dispo1;
        this.dispo2 = dispo2;
        this.dispo3 = dispo3;
        this.dispo4 = dispo4;
        this.dispo5 = dispo5;
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
}
