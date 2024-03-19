import java.util.*;
public class Request {
    private int id;
    private int duration;
    private int arrivalTime;
    private int waitingTime;

    public Request (int id, int duration, int arrivalTime){
        this.id = id;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setWaitingTime(int time) {
        waitingTime = time;
    }
    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", duration=" + duration +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
