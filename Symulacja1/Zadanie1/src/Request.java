import java.util.*;
public class Request {
    private int id;
    private int duration;
    private int arrivalTime;
    private int waitingTime;
    private int switches;

    public Request (int id, int duration, int arrivalTime){
        this.id = id;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.switches = 0;
    }
    public Request(){}

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int time) {
        waitingTime = time;
    }

    public void setDuration(int quantum) {
        this.duration -= quantum;
    } //przydatne jedynie do RR

    public void addSwitch() {
        this.switches++;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", duration=" + duration +
                ", arrivalTime=" + arrivalTime +
                ", switches= "+ switches+
                '}';
    }
}
