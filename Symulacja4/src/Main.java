import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int totalReferences = 10000;
        int[] arr = {1, 4, 11, 16, 26, 30, 42};
        List<Integer> process1 = Generator.generatePageReferences(arr[0], arr[1] - 1, totalReferences); //3 strony
        List<Integer> process2 = Generator.generatePageReferences(arr[1], arr[2] - 1, totalReferences); //7 stron
        List<Integer> process3 = Generator.generatePageReferences(arr[2], arr[3] - 1, totalReferences); //5 stron
        List<Integer> process4 = Generator.generatePageReferences(arr[3], arr[4] - 1, totalReferences); //10 stron
        List<Integer> process5 = Generator.generatePageReferences(arr[4], arr[5] - 1, totalReferences); //4 stron
        List<Integer> process6 = Generator.generatePageReferences(arr[5], arr[6] - 1, totalReferences); //12 stron

        generatedPrint(totalReferences, process2);
        List<List<Integer>> pageReferencesPerProcess = new ArrayList<>();

        pageReferencesPerProcess.add(process1);
        pageReferencesPerProcess.add(process2);
        pageReferencesPerProcess.add(process3);
        pageReferencesPerProcess.add(process4);
        pageReferencesPerProcess.add(process5);
        pageReferencesPerProcess.add(process6);

        int pagesVariety = arr[6] - 1 - arr[0] - 1; //max - min + 1

        int ramSize = 30;

        int timeWindow = 60;
        double lowerThreshold = 0.01;
        double upperThreshold = 0.2;
        int c = 30;

        System.out.println("\nEqual Allocation");
        EqualAllocation equalAllocation = new EqualAllocation(pageReferencesPerProcess, ramSize);
        int equalPageFaults = equalAllocation.simulate();

        System.out.println("\nProportional Allocation");
        ProportionalAllocation proportionalAllocation = new ProportionalAllocation(pageReferencesPerProcess, ramSize, pagesVariety);
        int proportionalPageFaults = proportionalAllocation.simulate();

        System.out.println("\nControl Frequency");
        ControlFrequency controlFrequency = new ControlFrequency(pageReferencesPerProcess, ramSize, timeWindow, lowerThreshold, upperThreshold, pagesVariety);
        int CFPageFaults = controlFrequency.simulate();

        System.out.println("\nModel Strefowy");
        ModelStrefowy modelStrefowy  = new ModelStrefowy(pageReferencesPerProcess, ramSize, c);
        int MSPageFaults = modelStrefowy.simulate();

        System.out.printf("%-25s %-15s %10s \n", "Algorytm", "Bledy strony", "Procent");
        System.out.printf("%-25s %-15d %9.2f%% \n","Equal", equalPageFaults, (double) equalPageFaults / (totalReferences * pageReferencesPerProcess.size()) * 100);
        System.out.printf("%-25s %-15d %9.2f%% \n","Proportional", proportionalPageFaults, (double) proportionalPageFaults / (totalReferences  * pageReferencesPerProcess.size()) * 100);
        System.out.printf("%-25s %-15d %9.2f%% \n","Control Frequency", CFPageFaults, (double) CFPageFaults / (totalReferences * pageReferencesPerProcess.size()) * 100);
        System.out.printf("%-25s %-15d %9.2f%% \n","Model Strefowy", MSPageFaults, (double) MSPageFaults / (totalReferences * pageReferencesPerProcess.size()) * 100);



    }
    private static void generatedPrint(int totalReferences, List<Integer> pageReferences){
        System.out.println("Wygenerowany ciąg odwołań:");
        for (int i = 0; i < totalReferences; i++) {
            System.out.print(pageReferences.get(i) + " ");
            if ((i + 1) % 20 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}