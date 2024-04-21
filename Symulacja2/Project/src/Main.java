import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        String fileName2 = "RealTimeRequestsList.txt";
        ArrayList<Request> normalRequests = readRequestsFile(fileName, false);
        ArrayList<Request> rtRequests = readRequestsFile(fileName2, true);

        ArrayList<Request> testRequests = new ArrayList<>();
        testRequests.add(new Request(1, 98, 0));
        testRequests.add(new Request(1, 183, 0));
        testRequests.add(new Request(1, 37, 0));
        testRequests.add(new Request(1, 122, 0));
        testRequests.add(new Request(1, 14, 0));
        testRequests.add(new Request(1, 124, 0));
        testRequests.add(new Request(1, 65, 0));
        testRequests.add(new Request(1, 67, 0));

        int headPosition = 53;
        int diskSize = 183;

        //FCFS fcfs = new FCFS(headPosition, testRequests);
        //fcfs.simulateAlgorithm();
        //SSTF sstf = new SSTF(headPosition, testRequests);
        //sstf.simulateAlgorithm();
        //SCAN scan = new SCAN(headPosition, diskSize, testRequests);
        //scan.simulateAlgorithm();
        CSCAN cscan = new CSCAN(headPosition, diskSize, testRequests);
        cscan.simulateAlgorithm();


    }
    private static ArrayList<Request> readRequestsFile(String fileName, boolean isRealTime) {

        ArrayList<Request> requests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            int id = 0;
            int position = 0;
            int arrivalTime = 0;
            int deadLine = 0;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("ID: ")) {
                    id = Integer.parseInt(line.substring(4));
                }
                else if (line.startsWith("Position: ")) {
                    position = Integer.parseInt(line.substring(10));
                }
                else if (line.startsWith("Arrival Time: ")) {
                    arrivalTime = Integer.parseInt(line.substring(14));
                }
                else if (isRealTime && line.startsWith("Deadline: ")) {
                    deadLine = Integer.parseInt(line.substring(10));
                }
                else if (!isRealTime && line.startsWith("Arrival Time: ")) {
                    requests.add(new Request(id, position, arrivalTime));
                }
                else if (isRealTime && line.startsWith("Arrival Time: ")) {
                    requests.add(new RealTimeRequest(id, position, arrivalTime, deadLine));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests;
    }

}