import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SSTF implements Simulation {
    private int headPosition;
    private ArrayList<Request> requestsList;

    public SSTF(int headPosition, ArrayList<Request> requestsList) {
        if (requestsList == null) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.requestsList = requestsList;
        this.headPosition = headPosition;
    }

    public Result simulateAlgorithm() {

        ArrayList<Request> copyRequestList = new ArrayList<>(requestsList);

        int currentTime = 0;
        int totalMovement = 0;

        while (!copyRequestList.isEmpty()) {

            Collections.sort(copyRequestList, Comparator.comparingInt(r -> Math.abs(headPosition - r.getPosition())));
            Request closestRequest = null;

            for (Request request : copyRequestList) {
                if (request.getArrivalTime() <= currentTime) {
                    closestRequest = request;
                    break;
                }
            }

            if (closestRequest == null) {
                currentTime++;
                continue;
            }

            totalMovement += Math.abs(headPosition - closestRequest.getPosition());
            currentTime += Math.abs(headPosition - closestRequest.getPosition());
            headPosition = closestRequest.getPosition();
            copyRequestList.remove(closestRequest);

        }


        return new Result("SSTF", totalMovement);

    }
}