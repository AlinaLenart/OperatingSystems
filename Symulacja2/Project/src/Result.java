public class Result {
    private final String simulationName;
    private final int totalMovement;
    private final int starvedRequests; //?
    private final int uncompletedRequests;



    public Result(String simulationName, int totalMovement) {

        this.simulationName = simulationName;
        this.totalMovement = totalMovement;
        this.starvedRequests = 0;
        this.uncompletedRequests = 0;

        System.out.println("Result of " + simulationName + " simulation: " +
                "\n- Total Movement: " + totalMovement +
                "\n- Starved Requests: " + starvedRequests);
    }
    public Result(String simulationName, int totalMovement, int uncompletedRequests) {

        this.simulationName = simulationName;
        this.totalMovement = totalMovement;
        this.starvedRequests = 0;
        this.uncompletedRequests = uncompletedRequests;

        System.out.println("Result of " + simulationName + " simulation: " +
                "\n- Total Movement: " + totalMovement +
                "\n- Starved Requests: " + starvedRequests +
                "\n- Uncompleted Requests: " + uncompletedRequests);
    }

    @Override
    public String toString() {
        return "Result of " + simulationName + " simulation: " +
                "\n- Total Movement: " + totalMovement +
                "\n- Starved Requests: " + starvedRequests;
    }
}