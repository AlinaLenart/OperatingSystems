import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SSTF_EDF {
    private int headPosition;
    private ArrayList<Request> normalRequestsList;
    private ArrayList<RealTimeRequest> realTimeRequestsList;

    public SSTF_EDF(int headPosition, ArrayList<Request> normalRequestsList, ArrayList<RealTimeRequest> realTimeRequestsList) {
        if (normalRequestsList.isEmpty() && realTimeRequestsList.isEmpty()) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.normalRequestsList = normalRequestsList;
        this.realTimeRequestsList = realTimeRequestsList;
        this.headPosition = headPosition;
    }

    public Result simulationSSTF_EDF() {

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
                    closestRealTimeRequest = request;
                    break;
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

            //TODO dodaj ze jezeli nie wyrobi sie to count niezrobione realTime & dodaje nie caly movement a tylko deadline


            //sprawdz czy dziala

            if (chosenRequest instanceof RealTimeRequest) {
                RealTimeRequest rtr = (RealTimeRequest) chosenRequest;
                if ((Math.abs(headPosition - chosenRequest.getPosition()) > rtr.getDeadline())) {
                    uncompletedRequests++;
                    totalMovement += rtr.getDeadline();
                    headPosition += (headPosition < rtr.getPosition()) ? rtr.getDeadline() : -rtr.getDeadline();
                    copyRealTimeRequestList.remove(chosenRequest);
                    break;
                }
                copyRealTimeRequestList.remove(chosenRequest);
                continue;
            }

            if (chosenRequest instanceof Request) {
                copyNormalRequestList.remove(chosenRequest);
            }

            totalMovement += Math.abs(headPosition - chosenRequest.getPosition());
            headPosition = chosenRequest.getPosition();

            currentTime++; //TODO ogarnij currentTime
        }

        return new Result("SSTF with EDF strategy", totalMovement, uncompletedRequests);
    }
}