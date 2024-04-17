import java.util.*;
public class SCAN {
    private int headPosition;
    private ArrayList<Request> requestsList;

    public SCAN(int headPosition, ArrayList<Request> requestsList) {
        if (requestsList == null) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.requestsList = requestsList;
        this.headPosition = headPosition;
    }

    public Result simulationSCAN() {

        ArrayList<Request> copyRequestList = new ArrayList<>();

        for (Request request : requestsList) {

            Request copyRequest = new Request(
                    request.getId(),
                    request.getPosition(),
                    request.getArrivalTime());

            copyRequestList.add(copyRequest);
        }

        int totalMovement = 0;
        boolean movingRight = true;
        int currentTime = 0;

        while (!requestsList.isEmpty()) {
            for (Iterator<Request> iterator = requestsList.iterator(); iterator.hasNext(); ) {
                Request request = iterator.next();
                if ((movingRight && request.getPosition() >= headPosition) || (!movingRight && request.getPosition() <= headPosition)) {
                    if (request.getArrivalTime() <= currentTime) {
                        totalMovement += Math.abs(headPosition - request.getPosition());
                        headPosition = request.getPosition();
                        iterator.remove();
                    }
                }
            }
            movingRight = !movingRight;
            currentTime++;
        }
        return new Result("SCAN", totalMovement);
    }


}