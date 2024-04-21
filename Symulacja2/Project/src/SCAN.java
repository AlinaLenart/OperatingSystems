import java.util.*;
public class SCAN implements Simulation {
    private int headPosition;
    private int diskSize;
    private ArrayList<Request> requestsList;

    public SCAN(int headPosition, int diskSize, ArrayList<Request> requestsList) {
        if (requestsList == null) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.requestsList = requestsList;
        this.headPosition = headPosition;
        this.diskSize = diskSize;
    }

    public Result simulateAlgorithm() {

        ArrayList<Request> copyRequestList = new ArrayList<>(requestsList);

        int totalMovement = 0;
        boolean movingRight = true;
        int currentTime = 0;

        while (!copyRequestList.isEmpty()) {

            if (movingRight) {
                Collections.sort(copyRequestList, Comparator.comparingInt(Request::getPosition));
            } else {
                Collections.sort(copyRequestList, Comparator.comparingInt(Request::getPosition).reversed());
            }

            for (Iterator<Request> iterator = copyRequestList.iterator(); iterator.hasNext(); ) {
                Request request = iterator.next();
                if ((movingRight && request.getPosition() >= headPosition) || (!movingRight && request.getPosition() <= headPosition)) {
                    if (request.getArrivalTime() <= currentTime) {
                        totalMovement += Math.abs(headPosition - request.getPosition());
                        currentTime += Math.abs(headPosition - request.getPosition());
                        headPosition = request.getPosition();
                        iterator.remove();
                    }
                }
            }

            if (movingRight && headPosition < diskSize) {
                totalMovement += (diskSize - headPosition);
            }

            else if (!movingRight && headPosition > 1){
                totalMovement += headPosition;
            }

            headPosition = movingRight ? diskSize : 1;
            movingRight = !movingRight;
            currentTime++;

        }
        return new Result("SCAN", totalMovement);
    }


}