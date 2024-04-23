public class Result {
    private final String simulationName;
    private final int totalMovement;
    private final int starvedRequests; //?
    private final int uncompletedRequests;
    private final int comingBack;



    public Result(String simulationName, int totalMovement, int starvedRequests) {

        this.simulationName = simulationName;
        this.totalMovement = totalMovement;
        this.starvedRequests = starvedRequests;
        this.uncompletedRequests = 0;
        this.comingBack = 0;

        System.out.println("Result of " + simulationName + " simulation: " +
                "\n- Total Movement: " + totalMovement +
                "\n- Starved Requests: " + starvedRequests);
    }
    public Result(int comingBack, String simulationName, int totalMovement, int starvedRequests) {

        this.simulationName = simulationName;
        this.totalMovement = totalMovement;
        this.starvedRequests = starvedRequests;
        this.uncompletedRequests = 0;
        this.comingBack = comingBack;

        System.out.println("Result of " + simulationName + " simulation: " +
                "\n- Total Movement: " + totalMovement +
                "\n- Starved Requests: " + starvedRequests +
                "\n- Head returning: " + comingBack);
    }
    public Result(String simulationName, int totalMovement, int uncompletedRequests, int starvedRequests) {

        this.simulationName = simulationName;
        this.totalMovement = totalMovement;
        this.starvedRequests = starvedRequests;
        this.uncompletedRequests = uncompletedRequests;
        this.comingBack = 0;

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