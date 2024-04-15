import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class RR {
    private int currentTime;
    private int starvedTime;
    private ArrayList<Request> requestsList;
    private int quantum;
    public RR (int starvedTime, ArrayList<Request> requestsList, int quantum){
        if (requestsList == null) {
            throw new NullPointerException();
        }
        this.quantum = quantum;
        this.requestsList = requestsList;
        this.starvedTime = starvedTime;
        this.currentTime = 0;
    }
    public Result simulationRR () {

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



        //utworzenie listy z taskami o najmniejszym arrivalTime
        //najpierw wykonuje pierwszy - przez kwant czasu
        //patrzy czy jakies nie doszly, wtedy dodaje je do listy
        //po tym usuwa go z poczatku i bierze go na koniec
        //potem kolejny w kolejce task  - przez kwant czasu - powtorka

        int totalWaitingTime = 0;
        int longestWaitingTime = 0;
        int totalSwitches = requestsList.size();
        int starvedTasksCount = 0;
        boolean addPrevious = false;
        Request first = new Request();
        ArrayList<Request> activeRequests = new ArrayList<>();

        while (!copyRequestList.isEmpty() || !activeRequests.isEmpty()){

            Iterator<Request> iterator = copyRequestList.iterator();

            while (iterator.hasNext()) {
                Request req = iterator.next();
                if (req.getArrivalTime() <= currentTime) {
                    activeRequests.add(req);
                    iterator.remove(); // iterator bo na forEach nie mozna usuwac
                }
            }

            if (addPrevious) {
                activeRequests.add(first); //na koniec aktywnych dodaje przed chwila procesowany task
            }

            if(!activeRequests.isEmpty()){

                first = activeRequests.getFirst();

                if (currentTime < first.getArrivalTime()) {

                    currentTime = first.getArrivalTime() + quantum;
                }
                else {

                    currentTime += quantum; //kwant czasu jest niepodzielny, jesli pozostale duration jest mniejsze to czas na proces i tak quantum
                }

                first.setDuration(quantum);

                activeRequests.removeFirst();

                if (first.getDuration() <= 0) {

                    first.setWaitingTime(Math.max(0, currentTime + first.getDuration() - first.getArrivalTime())); //currentTime jest wtedy kiedy KONCZY robic, czyli najpierw zmiana o quantum
                    totalWaitingTime += first.getWaitingTime();
                    longestWaitingTime = Math.max(longestWaitingTime, first.getWaitingTime());

                    if (first.getWaitingTime() > starvedTime) {
                        starvedTasksCount++;
                    }
                    addPrevious = false;
                }

                else {
                    addPrevious = true;
                    first.addSwitch();
                    totalSwitches++;
                }

            }
            else {
                currentTime++;
            }


        }


        int averageWaitingTime = totalWaitingTime / requestsList.size();
        return new Result("RR", averageWaitingTime, longestWaitingTime, totalSwitches, starvedTasksCount);



    }




}
