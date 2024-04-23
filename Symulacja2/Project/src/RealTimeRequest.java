public class RealTimeRequest extends Request{

    private int deadline;

    public RealTimeRequest(int id, int position, int arrivalTime, int deadline) {
        super(id, position, arrivalTime);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "RealTimeRequest{" +
                "deadline=" + deadline +
                '}';
    }

    public int getDeadline() {
        return deadline;
    }
}
