import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        createWithoutDeviation(fileName);

    }


    private static void createWithoutDeviation(String fileName){
        int n = 1000;
        int maxDuration = 20;
        int maxArrivalTime = 1000;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 1; i <= n; i++) {

                Request request = new Request(maxDuration, maxArrivalTime);
                writer.write(request.toString(i));

            }
            System.out.println("Tasks successfully saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


