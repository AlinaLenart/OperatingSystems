public class SimulationResult {
    private double meanLoad;
    private double stddev;
    private Statistics statistics;
    private boolean allTasksCompleted;

    public SimulationResult(double meanLoad, double stddev, Statistics statistics, boolean allTasksCompleted) {
        this.meanLoad = meanLoad;
        this.stddev = stddev;
        this.statistics = statistics;
        this.allTasksCompleted = allTasksCompleted;
    }

    public double getMeanLoad() {
        return meanLoad;
    }

    public double getStddev() {
        return stddev;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public boolean areAllTasksCompleted() {
        return allTasksCompleted;
    }
}
