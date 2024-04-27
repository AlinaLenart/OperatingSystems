package Algorithms;
import Core.Result;
import Core.Request;
import Core.RealTimeRequest;

import Algorithms.Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SSTF_EDF implements Simulation {
    private int headPosition;
    private ArrayList<Request> normalRequestsList;
    private ArrayList<RealTimeRequest> realTimeRequestsList;
    private final int starvedTime;
    private int currentTime;
    private int uncompletedRequests;

    public SSTF_EDF(int headPosition, ArrayList<Request> normalRequestsList, ArrayList<RealTimeRequest> realTimeRequestsList, int starvedTime) {
        if (normalRequestsList.isEmpty() && realTimeRequestsList.isEmpty()) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.normalRequestsList = new ArrayList<>(normalRequestsList);
        this.realTimeRequestsList = new ArrayList<>(realTimeRequestsList);
        this.headPosition = headPosition;
        this.starvedTime = starvedTime;
        this.currentTime= 0;
        this.uncompletedRequests = 0;
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

                RealTimeRequest rtr = (RealTimeRequest) chosenRequest;

                if ((Math.abs(headPosition - rtr.getPosition())) > rtr.getDeadline()) { //nie zdazy na zrobienie

                    uncompletedRequests++;
                    totalMovement += rtr.getDeadline();
                    currentTime += rtr.getDeadline();

                    if (headPosition < rtr.getPosition()){
                        headPosition += rtr.getPosition();
                    }
                    else{
                        headPosition -= rtr.getPosition();
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

        Result result = new Result("SSTF with EDF strategy", totalMovement, starvedRequests);
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

        Collections.sort(realTimeRequestsList, Comparator.comparingInt(RealTimeRequest::getDeadline));

        RealTimeRequest closestRealTimeRequest = null;

        Iterator<RealTimeRequest> iterator = realTimeRequestsList.iterator();
        while (iterator.hasNext()) {
            RealTimeRequest request = iterator.next();
            if (request.getArrivalTime() <= currentTime) {
                if ((request.getDeadline() + request.getArrivalTime()) <= currentTime) { // Drugi warunek jest na miniete requesty

                    uncompletedRequests++;
                    iterator.remove();
                    continue;
                }
                closestRealTimeRequest = request;
                break;
            }
        }


        return closestRealTimeRequest;
    }
}