import Algorithms.*;
import Core.RealTimeRequest;
import Core.Request;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        int headPosition = 1;
        int starvedTime = 750;
        int smallDiskSize = 100;
        int bigDiskSize = 500;
        testSmallAmountSmallDisk(headPosition, starvedTime, smallDiskSize);
        testBigAmountSmallDisk(headPosition, starvedTime, smallDiskSize);
        testSmallAmountBigDisk(headPosition, starvedTime, bigDiskSize);
        testBigAmountBigDisk(headPosition, starvedTime, bigDiskSize);
    }
    private static void testSmallAmountSmallDisk(int headPosition, int starvedTime, int smallDiskSize){

        String fileName = "SSRequestsList.txt";
        String fileName2 = "SSRealTimeRequestsList.txt";

        ArrayList<Request> normalRequests = readRequestsFile(fileName);
        ArrayList<RealTimeRequest> rtRequests = readRTRequestsFile(fileName2);

        System.out.println("\n---Small Amount Small Disk: ");

        FCFS fcfs = new FCFS(headPosition, normalRequests, starvedTime);
        fcfs.simulateAlgorithm();

        SSTF sstf = new SSTF(headPosition, normalRequests, starvedTime);
        sstf.simulateAlgorithm();

        SCAN scan = new SCAN(headPosition, smallDiskSize, normalRequests, starvedTime);
        scan.simulateAlgorithm();

        CSCAN cscan = new CSCAN(headPosition, smallDiskSize, normalRequests, starvedTime);
        cscan.simulateAlgorithm();

        SSTF_EDF sstf_edf = new SSTF_EDF(headPosition, normalRequests, rtRequests, starvedTime);
        sstf_edf.simulateAlgorithm();

        SSTF_FDSCAN sstf_fdscan = new SSTF_FDSCAN(headPosition, normalRequests, rtRequests, starvedTime);
        sstf_fdscan.simulateAlgorithm();
    }
    private static void testBigAmountSmallDisk(int headPosition, int starvedTime, int smallDiskSize){
        String fileName = "BSRequestsList.txt";
        String fileName2 = "BSRealTimeRequestsList.txt";

        ArrayList<Request> normalRequests = readRequestsFile(fileName);
        ArrayList<RealTimeRequest> rtRequests = readRTRequestsFile(fileName2);

        System.out.println("\n---Big Amount Small Disk: ");

        FCFS fcfs = new FCFS(headPosition, normalRequests, starvedTime);
        fcfs.simulateAlgorithm();

        SSTF sstf = new SSTF(headPosition, normalRequests, starvedTime);
        sstf.simulateAlgorithm();

        SCAN scan = new SCAN(headPosition, smallDiskSize, normalRequests, starvedTime);
        scan.simulateAlgorithm();

        CSCAN cscan = new CSCAN(headPosition, smallDiskSize, normalRequests, starvedTime);
        cscan.simulateAlgorithm();

        SSTF_EDF sstf_edf = new SSTF_EDF(headPosition, normalRequests, rtRequests, starvedTime);
        sstf_edf.simulateAlgorithm();

        SSTF_FDSCAN sstf_fdscan = new SSTF_FDSCAN(headPosition, normalRequests, rtRequests, starvedTime);
        sstf_fdscan.simulateAlgorithm();
    }
    private static void testSmallAmountBigDisk(int headPosition, int starvedTime, int bigDiskSize){
        String fileName = "SBRequestsList.txt";
        String fileName2 = "SBRealTimeRequestsList.txt";

        ArrayList<Request> normalRequests = readRequestsFile(fileName);
        ArrayList<RealTimeRequest> rtRequests = readRTRequestsFile(fileName2);

        System.out.println("\n---Small Amount Big Disk: ");

        FCFS fcfs = new FCFS(headPosition, normalRequests, starvedTime);
        fcfs.simulateAlgorithm();

        SSTF sstf = new SSTF(headPosition, normalRequests, starvedTime);
        sstf.simulateAlgorithm();

        SCAN scan = new SCAN(headPosition, bigDiskSize, normalRequests, starvedTime);
        scan.simulateAlgorithm();

        CSCAN cscan = new CSCAN(headPosition, bigDiskSize, normalRequests, starvedTime);
        cscan.simulateAlgorithm();

        SSTF_EDF sstf_edf = new SSTF_EDF(headPosition, normalRequests, rtRequests, starvedTime);
        sstf_edf.simulateAlgorithm();

        SSTF_FDSCAN sstf_fdscan = new SSTF_FDSCAN(headPosition, normalRequests, rtRequests, starvedTime);
        sstf_fdscan.simulateAlgorithm();
    }
    private static void testBigAmountBigDisk(int headPosition, int starvedTime, int bigDiskSize){
        String fileName = "BBRequestsList.txt";
        String fileName2 = "BBRealTimeRequestsList.txt";

        ArrayList<Request> normalRequests = readRequestsFile(fileName);
        ArrayList<RealTimeRequest> rtRequests = readRTRequestsFile(fileName2);

        System.out.println("\n---Big Amount Big Disk: ");

        FCFS fcfs = new FCFS(headPosition, normalRequests, starvedTime);
        fcfs.simulateAlgorithm();

        SSTF sstf = new SSTF(headPosition, normalRequests, starvedTime);
        sstf.simulateAlgorithm();

        SCAN scan = new SCAN(headPosition, bigDiskSize, normalRequests, starvedTime);
        scan.simulateAlgorithm();

        CSCAN cscan = new CSCAN(headPosition, bigDiskSize, normalRequests, starvedTime);
        cscan.simulateAlgorithm();

        SSTF_EDF sstf_edf = new SSTF_EDF(headPosition, normalRequests, rtRequests, starvedTime);
        sstf_edf.simulateAlgorithm();

        SSTF_FDSCAN sstf_fdscan = new SSTF_FDSCAN(headPosition, normalRequests, rtRequests, starvedTime);
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