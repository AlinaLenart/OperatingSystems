import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RR {
    public Result simulationRR (ArrayList<Request> requestsList, int switchTime) {

        //TODO wydziel do oddzielnej klasy
        ArrayList<Request> copyRequestList = new ArrayList<>();

        for (Request request : requestsList) {

            Request copyRequest = new Request(
                    request.getId(),
                    request.getDuration(),
                    request.getArrivalTime());

            copyRequestList.add(copyRequest);
        }

        Collections.sort(copyRequestList, Comparator.comparingInt(Request::getArrivalTime)
                .thenComparingInt(Request::getDuration));


        for (Request request : copyRequestList){
            System.out.println(request);
        }

        int totalWaitingTime = 0;
        int longestWaitingTime = 0;
        int totalSwitches = requestsList.size();
        int starvedTasksCount = 0;

        int currentTime = 0;


        while (!copyRequestList.isEmpty()){

            Request first = copyRequestList.get(0);
            for (Request req : copyRequestList) {

                if (req.getArrivalTime() <= currentTime) {
                    first = req;
                }
            }

            first.setWaitingTime(Math.max(0, currentTime - first.getArrivalTime()));
            totalWaitingTime += first.getWaitingTime();
            longestWaitingTime = Math.max(longestWaitingTime, first.getWaitingTime());

            if (first.getWaitingTime() > 30) {
                starvedTasksCount++;
            }

            if (currentTime < first.getArrivalTime()){
                currentTime = first.getArrivalTime() + first.getDuration();
            }
            else {
                currentTime += first.getDuration();
            }

            System.out.println("Wykonane: "+ first + " waiting time: "+first.getWaitingTime());

            if (first.getDuration() < 1) {
                copyRequestList.remove(first);
            }
            else {
                totalSwitches++;
            }

        }

        int averageWaitingTime = totalWaitingTime / requestsList.size();
        return new Result("SJF", averageWaitingTime, longestWaitingTime, totalSwitches, starvedTasksCount);



    }




}
