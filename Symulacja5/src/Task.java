public class Task {
    private double requiredLoad; // Zapotrzebowanie na moc obliczeniową w procentach
    private int executionTime; // Czas wykonania zadania w jednostkach czasu

    public Task(double requiredLoad, int executionTime) {
        this.requiredLoad = requiredLoad;
        this.executionTime = executionTime;
    }

    public double getRequiredLoad() {
        return requiredLoad;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void decrementExecutionTime() {
        this.executionTime--;
    }

    public boolean isCompleted() {
        return executionTime <= 0;
    }
}
