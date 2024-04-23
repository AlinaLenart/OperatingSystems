import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SSTF_FDSCAN implements Simulation {
    private int headPosition;
    private ArrayList<Request> normalRequestsList;
    private ArrayList<RealTimeRequest> realTimeRequestsList;
    private int starvedTime;

    public SSTF_FDSCAN(int headPosition, ArrayList<Request> normalRequestsList, ArrayList<RealTimeRequest> realTimeRequestsList, int starvedTime) {
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

                for (Iterator<Request> iterator = normalRequestsList.iterator(); iterator.hasNext(); ) { //sprzatanie normalnych po drodze
                    Request request = iterator.next();

                    if (chosenRequest.getPosition() <= headPosition) { // porusza się w lewo
                        if (request.getPosition() >= chosenRequest.getPosition() && request.getPosition() <= headPosition) {
                            System.out.println("Zrobione norm po drodze: " + request.toString());
                            iterator.remove();
                        }
                    }

                    else { // porusza się w prawo
                        if (request.getPosition() >= headPosition && request.getPosition() <= chosenRequest.getPosition()) {
                            System.out.println("Zrobione norm po drodze: " + request.toString());
                            iterator.remove();
                        }
                    }
                }

                realTimeRequestsList.remove(chosenRequest);
            }
            else {
                normalRequestsList.remove(chosenRequest);
            }


            totalMovement += Math.abs(headPosition - chosenRequest.getPosition());
            currentTime += Math.abs(headPosition - chosenRequest.getPosition());
            System.out.println("Zrobione: " + chosenRequest.toString());

            if ((currentTime - chosenRequest.getArrivalTime()) > starvedTime){
                starvedRequests++;
            }

            headPosition = chosenRequest.getPosition();

        }

        Result result = new Result("SSTF with FD-SCAN strategy", totalMovement, uncompletedRequests, starvedRequests);
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
                if ((request.getDeadline() + request.getArrivalTime()) < currentTime) { // drugi warunek jest na miniete requesty
                    uncompletedRequests++;
                    iterator.remove();
                    System.out.println("Odrzucone bo przeminal czas  RT: " + request.toString());
                } else if (request.getDeadline() > (Math.abs(headPosition - request.getPosition()))) { // realne do wykonania
                    closestRealTimeRequest = request;
                    System.out.println("Wybrane RT: " + closestRealTimeRequest.toString());
                    break;
                } else {
                    System.out.println("Niezdazylismy: " + request.toString());
                    uncompletedRequests++;
                }
            }
        }

        return closestRealTimeRequest;
    }
}