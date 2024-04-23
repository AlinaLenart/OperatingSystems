import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SSTF_EDF implements Simulation {
    private int headPosition;
    private ArrayList<Request> normalRequestsList;
    private ArrayList<RealTimeRequest> realTimeRequestsList;
    private final int starvedTime;

    public SSTF_EDF(int headPosition, ArrayList<Request> normalRequestsList, ArrayList<RealTimeRequest> realTimeRequestsList, int starvedTime) {
        if (normalRequestsList.isEmpty() && realTimeRequestsList.isEmpty()) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.normalRequestsList = new ArrayList<>(normalRequestsList);
        this.realTimeRequestsList = new ArrayList<>(realTimeRequestsList);
        this.headPosition = headPosition;
        this.starvedTime = starvedTime;
    }

    public Result simulateAlgorithm() {

        int currentTime = 0;
        int totalMovement = 0;
        int uncompletedRequests = 0;
        int starvedRequests = 0;

        while (!normalRequestsList.isEmpty() || !realTimeRequestsList.isEmpty()) {

            Request chosenRequest = null;
            RealTimeRequest closestRealTimeRequest = searchClosestRealTimeRequest(currentTime, uncompletedRequests);

            if (closestRealTimeRequest != null){
                chosenRequest = closestRealTimeRequest;
            }
            else {
                Request closestNormalRequest = searchClosestNormalRequest(currentTime);

                if (closestNormalRequest != null) {
                    chosenRequest = closestNormalRequest;
                }
                else {
                    currentTime++;
                    continue;
                }
            }


            if (chosenRequest instanceof RealTimeRequest) {

                RealTimeRequest rtr = (RealTimeRequest) chosenRequest;

                if ((Math.abs(headPosition - chosenRequest.getPosition()) > rtr.getDeadline())) { //nie zdazy na zrobienie

                    uncompletedRequests++;
                    totalMovement += rtr.getDeadline();
                    currentTime += rtr.getDeadline();
                    headPosition += (headPosition < rtr.getPosition()) ? rtr.getDeadline() : -rtr.getDeadline();
                    realTimeRequestsList.remove(chosenRequest);
                    System.out.println("Niezdazylismy: " + chosenRequest.toString());
                    continue;
                }

                realTimeRequestsList.remove(chosenRequest);
            }

            else {
                normalRequestsList.remove(chosenRequest);
            }

            totalMovement += Math.abs(headPosition - chosenRequest.getPosition());
            currentTime += Math.abs(headPosition - chosenRequest.getPosition());

            if ((currentTime - chosenRequest.getArrivalTime()) > starvedTime){
                starvedRequests++;
            }

            headPosition = chosenRequest.getPosition();
            System.out.println("Zrobione: " + chosenRequest.toString());

        }

        Result result = new Result("SSTF with EDF strategy", totalMovement, uncompletedRequests, starvedRequests);
        return result;
    }

    private Request searchClosestNormalRequest(int currentTime){

        Collections.sort(normalRequestsList, Comparator.comparingInt(r -> Math.abs(headPosition - r.getPosition())));

        Request closestNormalRequest = null;

        for (Request request : normalRequestsList) {
            if (request.getArrivalTime() <= currentTime) {
                closestNormalRequest = request;
                break;
            }
        }

        return closestNormalRequest;
    }
    private RealTimeRequest searchClosestRealTimeRequest(int currentTime, int uncompletedRequests){

        Collections.sort(realTimeRequestsList, Comparator.comparingInt(RealTimeRequest::getDeadline));

        RealTimeRequest closestRealTimeRequest = null;

        Iterator<RealTimeRequest> iterator = realTimeRequestsList.iterator();
        while (iterator.hasNext()) {
            RealTimeRequest request = iterator.next();
            if (request.getArrivalTime() <= currentTime) {
                if ((request.getDeadline() + request.getArrivalTime()) < currentTime) { // Drugi warunek jest na miniete requesty
                    uncompletedRequests++;
                    iterator.remove();
                    System.out.println("Odrzucone bo przeminal czas  RT: " + request.toString());
                    continue;
                }
                closestRealTimeRequest = request;
                System.out.println("Wybrane RT: " + closestRealTimeRequest.toString());
                break;
            }
        }


        return closestRealTimeRequest;
    }
}