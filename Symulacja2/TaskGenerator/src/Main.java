import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        String fileName2 = "RealTimeRequestsList.txt";
        int amount = 10;
        double ratio = 0.2;
        createRequestList(amount, fileName);
        createRealTimeRequestList(amount, ratio, fileName2);

    }


    private static void createRequestList(int amount, String fileName){
        int n = amount;
        int maxPosition = 50;
        int maxArrivalTime = 20;

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
    private static void createRealTimeRequestList(int amount, double ratio, String fileName){
        double n = amount * ratio;
        int maxPosition = 50;
        int maxArrivalTime = 20;
        int maxDeadline = 100;

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


