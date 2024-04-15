import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SSTF {
    private int headPosition;
    private ArrayList<Request> requestsList;

    public SSTF(int headPosition, ArrayList<Request> requestsList) {
        if (requestsList == null) {
            throw new NullPointerException("Lista żądań nie może być pusta.");
        }
        this.requestsList = requestsList;
        this.headPosition = headPosition;
    }

    public Result simulationSSTF() {

        ArrayList<Request> copyRequestList = new ArrayList<>();

        for (Request request : requestsList) {

            Request copyRequest = new Request(
                    request.getId(),
                    request.getPosition(),
                    request.getArrivalTime());

            copyRequestList.add(copyRequest);
        }

        int currentTime = 0;
        int totalMovement = 0;

        while (!copyRequestList.isEmpty()) {

            Collections.sort(copyRequestList, Comparator.comparingInt(r -> Math.abs(headPosition - r.getPosition())));
            Request closestRequest = null;

            // Znalezienie najbliższego żądania, które jest dostępne w odpowiednim czasie
            for (Request request : copyRequestList) {
                if (request.getArrivalTime() <= currentTime) {
                    closestRequest = request;
                    break;
                }
            }

            // Jeśli nie ma dostępnego żądania, zwiększ czas i kontynuuj pętlę
            if (closestRequest == null) {
                currentTime++;
                continue;
            }

            totalMovement += Math.abs(headPosition - closestRequest.getPosition()); // Dodanie odległości ruchu
            headPosition = closestRequest.getPosition(); // Aktualizacja pozycji głowicy
            currentTime++; // Zwiększenie czasu
            copyRequestList.remove(closestRequest); // Usunięcie żądania z listy
        }


        return new Result("SSTF", totalMovement);

    }
}