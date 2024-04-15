import java.util.*;
public class Request {
    private int position;
    private int arrivalTime;

    public Request(int meanDuration, int meanArrivalTime, double deviationDuration, double deviationArrivalTime) {
        Random generator = new Random();
        this.position = (int) Math.round(generator.nextGaussian() * deviationDuration + meanDuration);
        this.arrivalTime = (int) Math.round(generator.nextGaussian() * deviationArrivalTime + meanArrivalTime);
        if (this.position <= 0) {
            this.position = 1;
        }
        if (this.arrivalTime <= 0) {
            this.arrivalTime = 1;
        }
    }

    public Request(int maxDuration, int maxArrivalTime){ //bez rozkladu normalnego

        Random generator = new Random();
        this.position = generator.nextInt(maxDuration) + 1;
        this.arrivalTime = generator.nextInt(maxArrivalTime) + 1;

    }

    public String toString(int id) {
        return "ID: " + id +
                "\nPosition: " + position +
                "\nArrival Time: " + arrivalTime +
                "\n";
    }

    public int getPosition() {
        return position;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}