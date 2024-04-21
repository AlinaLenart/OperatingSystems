import java.util.*;
public class CSCAN implements Simulation{
    private int headPosition;
    private int diskSize;
    private ArrayList<Request> requestsList;

    public CSCAN(int headPosition, int diskSize, ArrayList<Request> requestsList) {
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
        int currentTime = 0;

        while (!copyRequestList.isEmpty()) {

            Collections.sort(copyRequestList, Comparator.comparingInt(Request::getPosition));

            for (Iterator<Request> iterator = copyRequestList.iterator(); iterator.hasNext(); ) {
                Request request = iterator.next();
                if (request.getPosition() >= headPosition) {
                    if (request.getArrivalTime() <= currentTime) {
                        totalMovement += Math.abs(headPosition - request.getPosition());
                        currentTime += Math.abs(headPosition - request.getPosition());
                        headPosition = request.getPosition();
                        iterator.remove();
                    }
                }
            }

            if (copyRequestList.isEmpty()){
                break;
            }

            if (headPosition < diskSize) {
                totalMovement += (diskSize - headPosition);
            }

            headPosition = 1;
            totalMovement += diskSize - 1;
            currentTime++;

        }
        return new Result("C-SCAN", totalMovement);
    }


}