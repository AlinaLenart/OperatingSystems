import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SSTF_FDSCAN {
    private int headPosition;
    private ArrayList<Request> normalRequestsList;
    private ArrayList<RealTimeRequest> realTimeRequestsList;

    public SSTF_FDSCAN(int headPosition, ArrayList<Request> normalRequestsList, ArrayList<RealTimeRequest> realTimeRequestsList) {
        if (normalRequestsList.isEmpty() && realTimeRequestsList.isEmpty()) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.normalRequestsList = normalRequestsList;
        this.realTimeRequestsList = realTimeRequestsList;
        this.headPosition = headPosition;
    }

    public Result simulationSSTF_FDSCAN() {

        ArrayList<Request> copyRequestList = new ArrayList<>();

        for (Request request : normalRequestsList) {
            Request copyRequest = new Request(
                    request.getId(),
                    request.getPosition(),
                    request.getArrivalTime());
            copyRequestList.add(copyRequest);
        }

        ArrayList<Request> copyNormalRequestList = new ArrayList<>(normalRequestsList);
        ArrayList<RealTimeRequest> copyRealTimeRequestList = new ArrayList<>(realTimeRequestsList);

        int currentTime = 0;
        int totalMovement = 0;
        int uncompletedRequests = 0;

        while (!copyNormalRequestList.isEmpty() || !copyRealTimeRequestList.isEmpty()) {

            Collections.sort(copyNormalRequestList, Comparator.comparingInt(r -> Math.abs(headPosition - r.getPosition())));
            Collections.sort(copyRealTimeRequestList, Comparator.comparingInt(RealTimeRequest::getDeadline));

            Request closestNormalRequest = null;
            RealTimeRequest closestRealTimeRequest = null;

            for (Request request : copyNormalRequestList) {
                if (request.getArrivalTime() <= currentTime) {
                    closestNormalRequest = request;
                    break;
                }
            }

            for (RealTimeRequest request : copyRealTimeRequestList) {
                if (request.getArrivalTime() <= currentTime && (request.getDeadline() + request.getArrivalTime()) > currentTime) {
                    if (request.getDeadline() > (Math.abs(headPosition - request.getPosition()))){ //realne do wykonania
                        closestRealTimeRequest = request;
                        break;
                    }
                    else {
                        uncompletedRequests++;
                    }
                }
            }

            Request chosenRequest = null;

            if (closestRealTimeRequest != null) {
                chosenRequest = closestRealTimeRequest;
            }
            else if (closestNormalRequest != null) {
                chosenRequest = closestNormalRequest;
            }

            if (chosenRequest == null) {
                currentTime++;
                continue;
            }


            if (chosenRequest instanceof RealTimeRequest) {
                copyRealTimeRequestList.remove(chosenRequest);
                continue;
            }

            if (chosenRequest instanceof Request) {
                copyNormalRequestList.remove(chosenRequest);
            }

            for (Iterator<Request> iterator = copyNormalRequestList.iterator(); iterator.hasNext(); ) {
                Request request = iterator.next();
                if (chosenRequest.getPosition() <= headPosition) { // porusza się w lewo
                    if (request.getPosition() >= chosenRequest.getPosition() && request.getPosition() <= headPosition) {
                        iterator.remove();
                    }
                } else { // porusza się w prawo
                    if (request.getPosition() >= headPosition && request.getPosition() <= chosenRequest.getPosition()) {
                        iterator.remove();
                    }
                }
            }

            totalMovement += Math.abs(headPosition - chosenRequest.getPosition());
            currentTime += Math.abs(headPosition - chosenRequest.getPosition());
            headPosition = chosenRequest.getPosition();

        }

        return new Result("SSTF with FD-SCAN strategy", totalMovement, uncompletedRequests);
    }
}