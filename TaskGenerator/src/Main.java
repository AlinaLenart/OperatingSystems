import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        int n = 10;
        String fileName = "RequestsList.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 1; i <= n; i++) {

                Request request = new Request(10, 20);
                writer.write(request.toString(i));

            }
            System.out.println("Tasks successfully saved to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}