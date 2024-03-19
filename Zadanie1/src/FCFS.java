import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS {
     public static void simulationFCFS (ArrayList<Request> requestsList) {

         //TODO wydziel do oddzielnej klasy
         ArrayList<Request> copyRequestList = new ArrayList<>();

         for (Request request : requestsList) {

             Request copyRequest = new Request(
                     request.getId(),
                     request.getDuration(),
                     request.getArrivalTime());

                 copyRequestList.add(copyRequest);
         }

         Collections.sort(copyRequestList, new Comparator<Request>() {
             @Override
             public int compare(Request r1, Request r2) {
                 return Integer.compare(r1.getArrivalTime(), r2.getArrivalTime());
             }
         });

         for (Request request : copyRequestList) {
             System.out.println(request);
         }






     }
}
