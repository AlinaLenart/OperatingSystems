import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        String fileName2 = "RealTimeRequestsList.txt";
        ArrayList<Request> normalRequests = readRequestsFile(fileName);
        ArrayList<RealTimeRequest> rtRequests = readRTRequestsFile(fileName2);

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
        int starvedtime = 1000;
        int diskSize = 183;

        /*FCFS fcfs = new FCFS(headPosition, testRequests, starvedtime);
        fcfs.simulateAlgorithm();

        SSTF sstf = new SSTF(headPosition, testRequests, starvedtime);
        sstf.simulateAlgorithm();

        SCAN scan = new SCAN(headPosition, diskSize, testRequests, starvedtime);
        scan.simulateAlgorithm();

        CSCAN cscan = new CSCAN(headPosition, diskSize, testRequests, starvedtime);
        cscan.simulateAlgorithm();*/

        SSTF_EDF sstf_edf = new SSTF_EDF(headPosition, normalRequests, rtRequests, starvedtime);
        sstf_edf.simulateAlgorithm();

        SSTF_FDSCAN sstf_fdscan = new SSTF_FDSCAN(headPosition, normalRequests, rtRequests, starvedtime);
        sstf_fdscan.simulateAlgorithm();

    }
    private static ArrayList<Request> readRequestsFile(String fileName) {

        ArrayList<Request> requests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            int id = 0;
            int position = 0;
            int arrivalTime = 0;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("ID: ")) {
                    id = Integer.parseInt(line.substring(4));
                }
                else if (line.startsWith("Position: ")) {
                    position = Integer.parseInt(line.substring(10));
                }
                else if (line.startsWith("Arrival Time: ")) {
                    arrivalTime = Integer.parseInt(line.substring(14));
                    requests.add(new Request(id, position, arrivalTime));
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests;
    }
    private static ArrayList<RealTimeRequest> readRTRequestsFile(String fileName) {

        ArrayList<RealTimeRequest> requests = new ArrayList<>();

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

                else if (line.startsWith("Deadline: ")) {
                    deadLine = Integer.parseInt(line.substring(10));
                }
                else if (line.startsWith("Arrival Time: ")) {
                    arrivalTime = Integer.parseInt(line.substring(14));
                    requests.add(new RealTimeRequest(id, position, arrivalTime, deadLine));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests;
    }


}