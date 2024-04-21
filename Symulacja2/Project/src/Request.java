public class Request {
    private int id;
    private int position;
    private int arrivalTime;
    private int deadline;

    public Request(int id, int position, int arrivalTime) {
        this.id = id;
        this.position = position;
        this.arrivalTime = arrivalTime;
    }

    public Request() {}

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}