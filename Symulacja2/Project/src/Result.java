public class Result {
    private final String simulationName;
    private final int totalMovement;
    private final int starvedTasksCount; //?



    public Result(String simulationName, int totalMovement) {

        this.simulationName = simulationName;
        this.totalMovement = totalMovement;
        this.starvedTasksCount = 0;

        System.out.println("Result of " + simulationName + " simulation: " +
                "\n- Total Movement: " + totalMovement +
                "\n- Starved Tasks: " + starvedTasksCount);
    }

    @Override
    public String toString() {
        return "Result of " + simulationName + " simulation: " +
                "\n- Total Movement: " + totalMovement +
                "\n- Starved Tasks: " + starvedTasksCount;
    }
}