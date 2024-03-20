import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS {
     public Result simulationFCFS (ArrayList<Request> requestsList) {

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


         int totalWaitingTime = 0;
         int longestWaitingTime = 0;
         int totalSwitches = requestsList.size();
         int starvedTasksCount = 0;

         int currentTime = 0;

         for (Request request : copyRequestList) {

             request.setWaitingTime(Math.max(0, currentTime - request.getArrivalTime()));
             totalWaitingTime += request.getWaitingTime();
             longestWaitingTime = Math.max(longestWaitingTime, request.getWaitingTime());

             if (request.getWaitingTime() > 30) {
                 starvedTasksCount++;
             }

             currentTime += request.getDuration();


         }

         int averageWaitingTime = totalWaitingTime / requestsList.size();

         return new Result("FCFS", averageWaitingTime, longestWaitingTime, totalSwitches, starvedTasksCount);



     }
}
