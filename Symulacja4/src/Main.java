import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int totalReferences = 10000;
        List<Integer> process1 = Generator.generatePageReferences(1, 3, totalReferences);
        List<Integer> process2 = Generator.generatePageReferences(4, 10, totalReferences);
        List<Integer> process3 = Generator.generatePageReferences(11, 15, totalReferences);
        List<Integer> process4 = Generator.generatePageReferences(16, 25, totalReferences);
        List<Integer> process5 = Generator.generatePageReferences(26, 30, totalReferences);
        List<Integer> process6 = Generator.generatePageReferences(31, 40, totalReferences);

        List<List<Integer>> pageReferencesPerProcess = new ArrayList<>();

        pageReferencesPerProcess.add(process1);
        pageReferencesPerProcess.add(process2);
        pageReferencesPerProcess.add(process3);
        pageReferencesPerProcess.add(process4);
        pageReferencesPerProcess.add(process5);
        pageReferencesPerProcess.add(process6);

        int pagesVariety = 40 - 1 + 1; //max - min + 1

        int ramSize = 30;

        int timeWindow = 80;
        double lowerThreshold = 0.01;
        double upperThreshold = 0.1;
        int calculationFrequency = 40;

        System.out.println("Equal Allocation");
        EqualAllocation equalAllocation = new EqualAllocation(pageReferencesPerProcess, ramSize);
        int equalPageFaults = equalAllocation.simulate();

        System.out.println("Proportional Allocation");
        ProportionalAllocation proportionalAllocation = new ProportionalAllocation(pageReferencesPerProcess, ramSize, pagesVariety);
        int proportionalPageFaults = proportionalAllocation.simulate();

        System.out.println("Control Frequency");
        ControlFrequency controlFrequency = new ControlFrequency(pageReferencesPerProcess, ramSize, timeWindow, lowerThreshold, upperThreshold, pagesVariety);
        int CFPageFaults = controlFrequency.simulate();

        System.out.println("Model Strefowy");
        ModelStrefowy modelStrefowy  = new ModelStrefowy(pageReferencesPerProcess, ramSize, calculationFrequency);
        int MSPageFaults = modelStrefowy.simulate();

        System.out.printf("%-25s %-15s %10s \n", "Algorytm", "Bledy strony", "Procent");
        System.out.printf("%-25s %-15d %8.2f%% \n","Equal", equalPageFaults, (double) equalPageFaults / totalReferences * pageReferencesPerProcess.size() * 100);
        System.out.printf("%-25s %-15d %8.2f%% \n","Proportional", proportionalPageFaults, (double) proportionalPageFaults / totalReferences * pageReferencesPerProcess.size() * 100);
        System.out.printf("%-25s %-15d %8.2f%% \n","Control Frequency", CFPageFaults, (double) CFPageFaults / totalReferences * pageReferencesPerProcess.size() * 100);
        System.out.printf("%-25s %-15d %8.2f%% \n","Model Strefowy", MSPageFaults, (double) MSPageFaults / totalReferences * pageReferencesPerProcess.size() * 100);



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