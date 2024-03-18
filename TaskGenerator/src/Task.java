import java.util.*;
public class Task {
    private int duration;
    private int arrivalTime;

    public Task (int maxDuration, int maxArrivalTime){

        Random generator = new Random();
        this.duration = generator.nextInt(maxDuration) + 1;
        this.arrivalTime = generator.nextInt(maxArrivalTime) + 1;

    }



}