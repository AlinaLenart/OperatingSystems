import java.util.*;

public class FCFS implements Simulation{
    private int headPosition;
    private ArrayList<Request> requestsList;
    private final int starvedTime;
    public FCFS (int headPosition, ArrayList<Request> requestsList, int starvedTime){

        if (requestsList == null) {
            throw new NullPointerException();
        }
        this.requestsList = new ArrayList<>(requestsList);
        this.headPosition = headPosition;
        this.starvedTime = starvedTime;
    }

    public Result simulateAlgorithm() {


        Collections.sort(requestsList, Comparator.comparingInt(Request::getArrivalTime).thenComparingInt(Request::getPosition));

        int totalMovement = 0;
        int starvedRequests = 0;

        while(!requestsList.isEmpty()) {

            Request request = requestsList.getFirst();

            totalMovement += Math.abs(headPosition - request.getPosition());
            headPosition = request.getPosition();

            if((totalMovement - request.getArrivalTime()) > starvedTime){
                starvedRequests++;
            }

            requestsList.removeFirst();

            Iterator<Request> iterator = requestsList.iterator();
            while (iterator.hasNext()) { //szukanie requestow o tej samej pozycji
                Request nextRequest = iterator.next();
                if (nextRequest.getPosition() == headPosition && nextRequest.getArrivalTime() <= totalMovement) {
                    if((totalMovement - nextRequest.getArrivalTime()) > starvedTime){
                        starvedRequests++;
                    }
                    iterator.remove();
                }
            }
        }

        return new Result("FCFS", totalMovement, starvedRequests);

    }
}
