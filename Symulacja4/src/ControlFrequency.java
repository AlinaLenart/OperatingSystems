import java.util.*;

public class ControlFrequency {
    private final List<List<Integer>> pageReferences;
    private final int ramSize;
    private final int processCount;
    private final int pagesVariety;
    private final int timeWindow; // okno czasowe dla pomiaru PPF
    private final double lowerThreshold; // L - dolny prog czestosci bledow strony
    private final double upperThreshold; // U - gorny prog czestosci bledow strony
    private int numCanceledProcesses; // Tracks the number of processes with no frames

    public ControlFrequency(List<List<Integer>> pageReferences, int ramSize, int timeWindow, double lowerThreshold, double upperThreshold, int pagesVariety) {
        this.pageReferences = new ArrayList<>(pageReferences);
        this.ramSize = ramSize;
        this.processCount = pageReferences.size();
        this.timeWindow = timeWindow;
        this.lowerThreshold = lowerThreshold;
        this.upperThreshold = upperThreshold;
        this.pagesVariety = pagesVariety;
        this.numCanceledProcesses = 0;
    }

    // Strategy 3: Page Fault Frequency Control
    public int simulate() {
        int totalPageFaults = 0;
        List<Integer> allocatedFramesPerProcess = new ArrayList<>(Collections.nCopies(processCount, 0));
        List<Integer> totalPageFaultsPerProcess = new ArrayList<>(Collections.nCopies(processCount, 0));

        // Initial proportional allocation
        for (int i = 0; i < processCount; i++) {
            List<Integer> pageReferences = this.pageReferences.get(i);
            int processVariety = Collections.max(pageReferences) - Collections.min(pageReferences) + 1;
            int framesPerProcess = (int) Math.floor((double) processVariety / pagesVariety * ramSize);
            allocatedFramesPerProcess.set(i, framesPerProcess);
        }

        // Simulate page references with PPF control over entire reference length
        int totalReferences = pageReferences.get(0).size();
        for (int t = 0; t < totalReferences; t += timeWindow) {
            List<Integer> currentPageFaultsPerProcess = new ArrayList<>(Collections.nCopies(processCount, 0));

            for (int i = 0; i < processCount; i++) {
                List<Integer> pageReferences = this.pageReferences.get(i).subList(t, Math.min(t + timeWindow, totalReferences));
                int framesPerProcess = allocatedFramesPerProcess.get(i);
                LRUSimulator lruSimulator = new LRUSimulator(pageReferences, framesPerProcess);
                int pageFaults = lruSimulator.simulateLRU();
                currentPageFaultsPerProcess.set(i, pageFaults);
                totalPageFaultsPerProcess.set(i, totalPageFaultsPerProcess.get(i) + pageFaults);
                totalPageFaults += pageFaults;

                // Check for no frames and mark process as canceled
                if (framesPerProcess == 0) {
                    numCanceledProcesses++;
                }
            }

            // Adjust frame allocation based on PPF
            for (int i = 0; i < processCount; i++) {
                double ppf = (double) currentPageFaultsPerProcess.get(i) / timeWindow;
                if (ppf > upperThreshold) {
                    allocatedFramesPerProcess.set(i, allocatedFramesPerProcess.get(i) + 1);
                } else if (ppf < lowerThreshold && allocatedFramesPerProcess.get(i) > 1) {
                    allocatedFramesPerProcess.set(i, allocatedFramesPerProcess.get(i) - 1);
                }
            }
        }

        // Print total page faults per process at the end of simulation
        for (int i = 0; i < processCount; i++) {
            System.out.println("Proces nr " + (i + 1) + " wygenerowal bledow: " + totalPageFaultsPerProcess.get(i));
        }

        System.out.println("Total canceled processes: " + numCanceledProcesses);
        return totalPageFaults;
    }
}
