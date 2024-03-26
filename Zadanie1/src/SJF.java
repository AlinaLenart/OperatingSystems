import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJF {
    private int starvedTime;
    private ArrayList<Request> requestsList;
    public SJF(int starvedTime, ArrayList<Request> requestsList){
        if (requestsList == null) {
            throw new NullPointerException();
        }
        this.starvedTime = starvedTime;
        this.requestsList = requestsList;
    }
    public Result simulationSJF () {

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

        while (!copyRequestList.isEmpty()){

            Request first = copyRequestList.getFirst();

            for (Request req : copyRequestList) {

                if (req.getArrivalTime() <= currentTime && first.getDuration() > req.getDuration()) {
                    first = req;
                }
            }

            if (currentTime < first.getArrivalTime()){

                currentTime = first.getArrivalTime() + first.getDuration();
            }
            else {
                currentTime += first.getDuration();
            }

            first.setWaitingTime(Math.max(0, currentTime - first.getArrivalTime()));
            totalWaitingTime += first.getWaitingTime();
            longestWaitingTime = Math.max(longestWaitingTime, first.getWaitingTime());
            copyRequestList.remove(first);

            if (first.getWaitingTime() > starvedTime) {
                starvedTasksCount++;
            }


        }

        int averageWaitingTime = totalWaitingTime / requestsList.size();
        return new Result("SJF", averageWaitingTime, longestWaitingTime, totalSwitches, starvedTasksCount);


    }

}
