public class Pair<A, B> {
    private final A employe;
    private final B mission;
    private final int fitness;
    private final double distance;

    public Pair(A employe, B mission, int fitness, double distance) {
        this.employe = employe;
        this.mission = mission;
        this.fitness = fitness;
        this.distance = distance;
    }

    public A getEmploye() {
        return employe;
    }

    public B getMission() {
        return mission;
    }

    public int getFitness() {
        return fitness;
    }

    public double getDistance() {
        return distance;
    }
}

