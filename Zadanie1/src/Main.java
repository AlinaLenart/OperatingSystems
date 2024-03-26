import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        ArrayList<Request> requests = readRequestsFile(fileName);


        int starvedTime = 150;

        consoleMenu(requests, starvedTime);


    }


    private static void consoleMenu(ArrayList<Request> requests, int starvedTime){
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit){
            System.out.println("\n   Choose simulation type:   ");
            System.out.printf("[1] FCFS \n[2] SJF \n[3] RR\n");
            int choosenType = scanner.nextInt();
            scanner.nextLine();
            switch(choosenType) {
                case 1:
                    FCFS fcfs = new FCFS(starvedTime, requests);
                    System.out.println(fcfs.simulationFCFS());
                    break;
                case 2:
                    SJF sjf = new SJF(starvedTime, requests);
                    System.out.println(sjf.simulationSJF());
                    break;
                case 3:
                    System.out.println("Give quantum: ");
                    int quantum = scanner.nextInt();
                    scanner.nextLine();
                    RR rr = new RR(starvedTime, requests, quantum);
                    System.out.println(rr.simulationRR());
                    break;
                default:
                    exit = true;
                    break;
            }

        }




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