public class Result {
    private final String simulationName;
    private final int averageWaitingTime;
    private final int longestWaitingTime;
    private final int totalSwitches;
    private final int starvedTasksCount;



    public Result(String simulationName, int averageWaitingTime, int longestWaitingTime, int totalSwitches, int starvedTasksCount) {
        this.simulationName = simulationName;
        this.averageWaitingTime = averageWaitingTime;
        this.longestWaitingTime = longestWaitingTime;
        this.totalSwitches = totalSwitches;
        this.starvedTasksCount = starvedTasksCount;
    }

    @Override
    public String toString() {
        return "Result of " + simulationName + " simulation: " +
                "\n- Average Waiting Time: " + averageWaitingTime +
                "\n- Longest Waiting Time: " + longestWaitingTime +
                "\n- Total Switches: " + totalSwitches +
                "\n- Starved Tasks: " + starvedTasksCount;
    }
}
