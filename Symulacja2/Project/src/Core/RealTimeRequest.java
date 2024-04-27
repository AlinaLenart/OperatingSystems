package Core;

public class RealTimeRequest extends Request {

    private int id;
    private int position;
    private int arrivalTime;
    private int deadline;

    public RealTimeRequest(int id, int position, int arrivalTime, int deadline) {
        super(id, position, arrivalTime);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Core.RealTimeRequest{" +
                "id=" + id +
                ", position=" + position +
                ", arrivalTime=" + arrivalTime +
                "deadline=" + deadline +
                '}';
    }

    public int getDeadline() {
        return deadline;
    }
}
