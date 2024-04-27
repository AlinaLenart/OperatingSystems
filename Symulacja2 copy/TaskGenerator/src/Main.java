import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        generateSmallAmountSmallDisk();
        generateBigAmountSmallDisk();
        generateSmallAmountBigDisk();
        generateBigAmountBigDisk();
    }
    private static void generateSmallAmountSmallDisk(){
        String fileName = "SSRequestsList.txt";
        String fileName2 = "SSRealTimeRequestsList.txt";
        int amount = 50;
        int maxPosition = 100;
        int maxArrivalTime = 200;
        int maxDeadline = 70;
        double ratio = 0.2;
        createRequestList(amount, fileName, maxPosition, maxArrivalTime);
        createRealTimeRequestList(amount, ratio, fileName2, maxPosition, maxArrivalTime, maxDeadline);
    }
    private static void generateBigAmountSmallDisk(){
        String fileName = "BSRequestsList.txt";
        String fileName2 = "BSRealTimeRequestsList.txt";
        int amount = 500;
        int maxPosition = 100;
        int maxArrivalTime = 200;
        int maxDeadline = 70;
        double ratio = 0.2;
        createRequestList(amount, fileName, maxPosition, maxArrivalTime);
        createRealTimeRequestList(amount, ratio, fileName2, maxPosition, maxArrivalTime, maxDeadline);
    }
    private static void generateSmallAmountBigDisk(){
        String fileName = "SBRequestsList.txt";
        String fileName2 = "SBRealTimeRequestsList.txt";
        int amount = 50;
        int maxPosition = 1000;
        int maxArrivalTime = 400;
        int maxDeadline = 200;
        double ratio = 0.2;
        createRequestList(amount, fileName, maxPosition, maxArrivalTime);
        createRealTimeRequestList(amount, ratio, fileName2, maxPosition, maxArrivalTime, maxDeadline);
    }
    private static void generateBigAmountBigDisk(){
        String fileName = "BBRequestsList.txt";
        String fileName2 = "BBRealTimeRequestsList.txt";
        int amount = 500;
        int maxPosition = 500;
        int maxArrivalTime = 400;
        int maxDeadline = 200;
        double ratio = 0.2;
        createRequestList(amount, fileName, maxPosition, maxArrivalTime);
        createRealTimeRequestList(amount, ratio, fileName2, maxPosition, maxArrivalTime, maxDeadline);
    }
    private static void testEdgeCase(){

    }

    private static void createRequestList(int amount, String fileName, int maxPosition, int maxArrivalTime){
        int n = amount;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 1; i <= n; i++) {

                Request request = new Request(maxPosition, maxArrivalTime);
                writer.write(request.toString(i));

            }
            System.out.println("Tasks successfully saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void createRealTimeRequestList(int amount, double ratio, String fileName, int maxPosition, int maxArrivalTime, int maxDeadline){
        double n = amount * ratio;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 1; i <= n; i++) {

                Request request = new Request(maxPosition, maxArrivalTime, maxDeadline);
                writer.write(request.rtToString(i));

            }
            System.out.println("Tasks successfully saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


