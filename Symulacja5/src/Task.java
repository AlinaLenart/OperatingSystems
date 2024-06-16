public class Task {
    private double requiredLoad; // zapotrzebowanie na moc obliczeniowa
    private int executionTime; // czas do zakonczenia
    private int arrivalTime;

    public Task(double requiredLoad, int executionTime, int arrivalTime) {
        this.requiredLoad = requiredLoad;
        this.executionTime = executionTime;
        this.arrivalTime = arrivalTime;
    }

    public double getRequiredLoad() {
        return requiredLoad;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void decrementExecutionTime(int deltaT) {
        this.executionTime -= deltaT;
    }

    public boolean isCompleted() {
        return executionTime <= 0;
    }
}
