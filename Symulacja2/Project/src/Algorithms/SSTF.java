package Algorithms;
import Core.Result;
import Core.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SSTF implements Simulation {
    private int headPosition;
    private ArrayList<Request> requestsList;
    private final int starvedTime;

    public SSTF(int headPosition, ArrayList<Request> requestsList, int starvedTime) {
        if (requestsList == null) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.requestsList = new ArrayList<>(requestsList);
        this.headPosition = headPosition;
        this.starvedTime = starvedTime;
    }

    public Result simulateAlgorithm() {

        int currentTime = 0;
        int totalMovement = 0;
        int starvedRequests = 0;

        while (!requestsList.isEmpty()) {

            Collections.sort(requestsList, Comparator.comparingInt(r -> Math.abs(headPosition - r.getPosition())));
            Request closestRequest = null;

            for (Request request : requestsList) {
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

            if((currentTime - closestRequest.getArrivalTime()) > starvedTime){
                starvedRequests++;
            }

            headPosition = closestRequest.getPosition();
            requestsList.remove(closestRequest);

        }


        return new Result("SSTF", totalMovement, starvedRequests);

    }
}