import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String fileName = "RequestsList.txt";
        createRequestList(fileName);

    }


    private static void createRequestList(String fileName){
        int n = 10;
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

}


