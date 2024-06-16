public class SimulationResult {
    private double meanLoad;
    private double stddev;
    private Statistics statistics;

    public SimulationResult(double meanLoad, double stddev, Statistics statistics) {
        this.meanLoad = meanLoad;
        this.stddev = stddev;
        this.statistics = statistics;
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

}
