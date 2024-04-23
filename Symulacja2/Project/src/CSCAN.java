import java.util.*;
public class CSCAN implements Simulation{
    private int headPosition;
    private int diskSize;
    private ArrayList<Request> requestsList;
    private final int starvedTime;

    public CSCAN(int headPosition, int diskSize, ArrayList<Request> requestsList, int starvedTime) {
        if (requestsList == null) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.requestsList = new ArrayList<>(requestsList);
        this.headPosition = headPosition;
        this.diskSize = diskSize;
        this.starvedTime = starvedTime;
    }

    public Result simulateAlgorithm() {

        int totalMovement = 0;
        int currentTime = 0;
        int countComingBack = 0;
        int starvedRequests = 0;

        while (!requestsList.isEmpty()) {

            Collections.sort(requestsList, Comparator.comparingInt(Request::getPosition));

            for (Iterator<Request> iterator = requestsList.iterator(); iterator.hasNext(); ) {

                Request request = iterator.next();

                if (request.getPosition() >= headPosition) {

                    if (request.getArrivalTime() <= currentTime) {

                        totalMovement += Math.abs(headPosition - request.getPosition());
                        currentTime += Math.abs(headPosition - request.getPosition());

                        if ((currentTime - request.getArrivalTime()) > starvedTime){
                            starvedRequests++;
                        }

                        headPosition = request.getPosition();
                        iterator.remove();
                    }
                }
            }

            if (requestsList.isEmpty()){
                break;
            }

            if (headPosition < diskSize) {
                totalMovement += (diskSize - headPosition);
            }

            headPosition = 1;
            totalMovement += diskSize - 1;
            countComingBack++;
            currentTime++;

        }

        return new Result(countComingBack, "C-SCAN", totalMovement, starvedRequests);
    }


}