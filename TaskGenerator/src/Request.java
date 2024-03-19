import java.util.*;
public class Request {
    private int duration;
    private int arrivalTime;

    public Request (int maxDuration, int maxArrivalTime){

        Random generator = new Random();
        this.duration = generator.nextInt(maxDuration) + 1;
        this.arrivalTime = generator.nextInt(maxArrivalTime) + 1;

    }

    public String toString(int id) {
        return "ID: " + id +
                "\nDuration: " + duration +
                "\nArrivalTime: " + arrivalTime +
                "\n\n";
    }

    public int getDuration() {
        return duration;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}