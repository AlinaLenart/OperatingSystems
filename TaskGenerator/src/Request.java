import java.util.*;
public class Request {
    private int duration;
    private int arrivalTime;

    public Request(int meanDuration, int meanArrivalTime, double deviationDuration, double deviationArrivalTime) {
        Random generator = new Random();
        this.duration = (int) Math.round(generator.nextGaussian() * deviationDuration + meanDuration);
        this.arrivalTime = (int) Math.round(generator.nextGaussian() * deviationArrivalTime + meanArrivalTime);
        if (this.duration <= 0) {
            this.duration = 1;
        }
        if (this.arrivalTime <= 0) {
            this.arrivalTime = 1;
        }
    }

    public Request (int maxDuration, int maxArrivalTime){ //bez rozkladu normalnego

        Random generator = new Random();
        this.duration = generator.nextInt(maxDuration) + 1;
        this.arrivalTime = generator.nextInt(maxArrivalTime) + 1;

    }

    public String toString(int id) {
        return "ID: " + id +
                "\nDuration: " + duration +
                "\nArrival Time: " + arrivalTime +
                "\n";
    }

    public int getDuration() {
        return duration;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}