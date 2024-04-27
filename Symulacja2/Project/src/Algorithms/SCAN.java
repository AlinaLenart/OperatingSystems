package Algorithms;
import Core.Result;
import Core.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
public class SCAN implements Simulation {
    private int headPosition;
    private int diskSize;
    private ArrayList<Request> requestsList;
    private final int starvedTime;

    public SCAN(int headPosition, int diskSize, ArrayList<Request> requestsList, int starvedTime) {
        if (requestsList == null) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.requestsList = new ArrayList<>(requestsList);
        this.headPosition = headPosition;
        this.diskSize = diskSize;
        this.starvedTime = starvedTime;
    }

    public Result simulateAlgorithm() {

        int totalMovement = 0;
        boolean movingRight = true;
        int currentTime = 0;
        int starvedRequests = 0;

        while (!requestsList.isEmpty()) {

            if (movingRight) {
                Collections.sort(requestsList, Comparator.comparingInt(Request::getPosition));
            } else {
                Collections.sort(requestsList, Comparator.comparingInt(Request::getPosition).reversed());
            }

            for (Iterator<Request> iterator = requestsList.iterator(); iterator.hasNext(); ) {

                Request request = iterator.next();

                if ((movingRight && request.getPosition() >= headPosition) || (!movingRight && request.getPosition() <= headPosition)) {

                    if (request.getArrivalTime() <= currentTime) {

                        totalMovement += Math.abs(headPosition - request.getPosition());
                        currentTime += Math.abs(headPosition - request.getPosition());

                        if ((currentTime - request.getArrivalTime()) > starvedTime){
                            starvedRequests++;
                        }

                        headPosition = request.getPosition();
                        iterator.remove();
                    }
                }
            }

            if (requestsList.isEmpty()){
                break;
            }

            if (movingRight && headPosition < diskSize) {
                totalMovement += (diskSize - headPosition);
                currentTime += (diskSize - headPosition);
            }

            else if (!movingRight && headPosition > 1){
                totalMovement += headPosition;
                currentTime += headPosition;
            }

            headPosition = movingRight ? diskSize : 1;
            movingRight = !movingRight;
            currentTime++;
        }
        return new Result("SCAN", totalMovement, starvedRequests);
    }


}