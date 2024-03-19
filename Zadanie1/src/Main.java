import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        ArrayList<Request> requests = readRequestsFile(fileName);

        //TODO opcja wpisania ktory algorytm uruchomic
//        Scanner scanner = new Scanner(System.in);

        FCFS.simulationFCFS(requests);



    }

    private static ArrayList<Request> readRequestsFile(String fileName) {

        ArrayList<Request> requests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int id = 0;
            int duration = 0;
            int arrivalTime = 0;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("ID: ")) {
                    id = Integer.parseInt(line.substring(4));
                }
                else if (line.startsWith("Duration: ")) {
                    duration = Integer.parseInt(line.substring(10));
                }
                else if (line.startsWith("Arrival Time: ")) {
                    arrivalTime = Integer.parseInt(line.substring(14));
                    requests.add(new Request(id, duration, arrivalTime));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests;
    }
}