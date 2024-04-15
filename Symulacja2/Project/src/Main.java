import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        //ArrayList<Request> requests = readRequestsFile(fileName);
        ArrayList<Request> requests = new ArrayList<>();
        requests.add(new Request(1, 98, 1));
        requests.add(new Request(1, 183, 1));
        requests.add(new Request(1, 37, 1));
        requests.add(new Request(1, 122, 1));
        requests.add(new Request(1, 14, 1));
        requests.add(new Request(1, 124, 1));
        requests.add(new Request(1, 65, 1));
        requests.add(new Request(1, 67, 1));



        int headPosition = 53;
        //FCFS fcfs = new FCFS(headPosition, requests);
        //fcfs.simulationFCFS();
        SSTF sstf = new SSTF(headPosition, requests);
        sstf.simulationSSTF();


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
}