package Algorithms;
import Core.Result;
import Core.Request;
import Core.RealTimeRequest;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SSTF_FDSCAN implements Simulation {
    private int headPosition;
    private ArrayList<Request> normalRequestsList;
    private ArrayList<RealTimeRequest> realTimeRequestsList;
    private int starvedTime;
    private int currentTime;
    private int uncompletedRequests;

    public SSTF_FDSCAN(int headPosition, ArrayList<Request> normalRequestsList, ArrayList<RealTimeRequest> realTimeRequestsList, int starvedTime) {
        if (normalRequestsList.isEmpty() && realTimeRequestsList.isEmpty()) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.normalRequestsList = new ArrayList<>(normalRequestsList);
        this.realTimeRequestsList = new ArrayList<>(realTimeRequestsList);
        this.headPosition = headPosition;
        this.starvedTime = starvedTime;
        this.uncompletedRequests = 0;
        this.currentTime = 0;
    }

    public Result simulateAlgorithm() {

        int totalMovement = 0;
        int starvedRequests = 0;

        while (!normalRequestsList.isEmpty() || !realTimeRequestsList.isEmpty()) {

            Request chosenRequest = null;
            RealTimeRequest closestRealTimeRequest = searchClosestRealTimeRequest();

            if (closestRealTimeRequest != null){
                chosenRequest = closestRealTimeRequest;
            }
            else {
                Request closestNormalRequest = searchClosestNormalRequest();

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
                            iterator.remove();
                        }
                    }

                    else { // porusza się w prawo
                        if (request.getPosition() >= headPosition && request.getPosition() <= chosenRequest.getPosition()) {
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

            if ((currentTime - chosenRequest.getArrivalTime()) > starvedTime){
                starvedRequests++;
            }

            headPosition = chosenRequest.getPosition();

        }

        Result result = new Result("SSTF with FD-SCAN strategy", totalMovement, starvedRequests);
        return result;
    }
    private Request searchClosestNormalRequest(){

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
    private RealTimeRequest searchClosestRealTimeRequest(){

        Collections.sort(realTimeRequestsList, Comparator.comparingInt(realTimeRequest -> Math.abs(headPosition - realTimeRequest.getPosition())));

        RealTimeRequest closestRealTimeRequest = null;

        Iterator<RealTimeRequest> iterator = realTimeRequestsList.iterator();
        while (iterator.hasNext()) {
            RealTimeRequest request = iterator.next();
            if (request.getArrivalTime() <= currentTime) {
                if ((request.getDeadline() + request.getArrivalTime()) <= currentTime) { // drugi warunek jest na miniete requesty
                    uncompletedRequests++;
                    iterator.remove();
                } else if (request.getDeadline() >= (Math.abs(headPosition - request.getPosition()))) { // realne do wykonania
                    closestRealTimeRequest = request;
                    break;
                } else {
                    uncompletedRequests++;
                }
            }
        }

        return closestRealTimeRequest;
    }
}