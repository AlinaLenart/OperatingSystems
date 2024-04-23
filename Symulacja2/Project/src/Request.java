public class Request {
    private int id;
    private int position;
    private int arrivalTime;

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

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", position=" + position +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}