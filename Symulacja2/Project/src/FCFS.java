import java.util.*;

public class FCFS {
    private int headPosition;
    private ArrayList<Request> requestsList;
    public FCFS (int headPosition, ArrayList<Request> requestsList){

        if (requestsList == null) {
            throw new NullPointerException();
        }
        this.requestsList = requestsList;
        this.headPosition = headPosition;
    }

    public Result simulationFCFS () {

        ArrayList<Request> copyRequestList = new ArrayList<>();

        for (Request request : requestsList) {

            Request copyRequest = new Request(
                    request.getId(),
                    request.getPosition(),
                    request.getArrivalTime());

            copyRequestList.add(copyRequest);
        }

        Collections.sort(copyRequestList, Comparator.comparingInt(Request::getArrivalTime).thenComparingInt(Request::getPosition));

        int totalMovement = 0;

        while(!copyRequestList.isEmpty()) {

            Request request = copyRequestList.getFirst();

            totalMovement += Math.abs(headPosition - request.getPosition()); // Dodanie odległości ruchu
            headPosition = request.getPosition(); // Aktualizacja pozycji głowicy
            copyRequestList.removeFirst();

            // Sprawdzenie, czy istnieje inne żądanie o tej samej pozycji
            Iterator<Request> iterator = copyRequestList.iterator();
            while (iterator.hasNext()) {
                Request nextRequest = iterator.next();
                if (nextRequest.getPosition() == headPosition && nextRequest.getArrivalTime() <= totalMovement) {
                    iterator.remove(); // Usunięcie wykonanego żądania z listy
                }
            }
        }

        return new Result("FCFS", totalMovement);

    }
}
