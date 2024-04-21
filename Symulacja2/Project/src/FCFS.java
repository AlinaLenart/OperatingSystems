import java.util.*;

public class FCFS implements Simulation{
    private int headPosition;
    private ArrayList<Request> requestsList;
    public FCFS (int headPosition, ArrayList<Request> requestsList){

        if (requestsList == null) {
            throw new NullPointerException();
        }
        this.requestsList = requestsList;
        this.headPosition = headPosition;
    }

    public Result simulateAlgorithm() {

        ArrayList<Request> copyRequestList = new ArrayList<>(requestsList);

        Collections.sort(copyRequestList, Comparator.comparingInt(Request::getArrivalTime).thenComparingInt(Request::getPosition));

        int totalMovement = 0;

        while(!copyRequestList.isEmpty()) {

            Request request = copyRequestList.getFirst();

            totalMovement += Math.abs(headPosition - request.getPosition());
            headPosition = request.getPosition();
            copyRequestList.removeFirst();

            Iterator<Request> iterator = copyRequestList.iterator();
            while (iterator.hasNext()) {
                Request nextRequest = iterator.next();
                if (nextRequest.getPosition() == headPosition && nextRequest.getArrivalTime() <= totalMovement) {
                    iterator.remove();
                }
            }
        }

        return new Result("FCFS", totalMovement);

    }
}
