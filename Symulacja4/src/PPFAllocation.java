import java.util.*;

public class PPFAllocation {
    private final List<List<Integer>> pageReferencesPerProcess;
    private final int ramSize;
    private final int processCount;
    private final double bottomThreshold; // dolny prog czestosci bledow strony
    private final double topThreshold; // gorny prog czestosci bledow strony
    private final int timeWindow; // okno czasowe dla pomiaru PPF
    public PPFAllocation(List<List<Integer>> pageReferencesPerProcess, int ramSize, double bottomThreshold, double topThreshold, int timeWindow) {
        this.pageReferencesPerProcess = pageReferencesPerProcess;
        this.ramSize = ramSize;
        this.processCount = pageReferencesPerProcess.size();
        this.bottomThreshold = bottomThreshold;
        this.topThreshold = topThreshold;
        this.timeWindow = timeWindow;
    }
    public int simulate() {
        int totalPageFaults = 0;
        List<Integer> allocatedFramesPerProcess = new ArrayList<>(processCount);

        // Step 1: Initial allocation using proportional allocation
        for (List<Integer> pageReferences : pageReferencesPerProcess) {
            int framesPerProcess = (int) Math.floor((double) pageReferences.size() / allPagesCount() * ramSize);
            allocatedFramesPerProcess.add(framesPerProcess);
            totalPageFaults += simulateLRU(pageReferences, framesPerProcess);
        }

        // Step 2: Monitor PPF and adjust frame allocation
        int[] pageFaultsHistory = new int[processCount];
        for (int i = 0; i < timeWindow; i++) {
            for (int j = 0; j < processCount; j++) {
                int pageFaults = simulateLRU(pageReferencesPerProcess.get(j), allocatedFramesPerProcess.get(j));
                pageFaultsHistory[j] += pageFaults;
            }
        }

        for (int i = timeWindow; i < allPagesCount(); i++) {
            for (int j = 0; j < processCount; j++) {
                int pageFaults = simulateLRU(pageReferencesPerProcess.get(j), allocatedFramesPerProcess.get(j));
                pageFaultsHistory[j] += pageFaults;

                double ppf = (double) pageFaultsHistory[j] / timeWindow;
                if (ppf > topThreshold && allocatedFramesPerProcess.get(j) < ramSize) {
                    // Increase frames allocation if PPF exceeds upper threshold
                    allocatedFramesPerProcess.set(j, allocatedFramesPerProcess.get(j) + 1);
                } else if (ppf < bottomThreshold && allocatedFramesPerProcess.get(j) > 0) {
                    // Decrease frames allocation if PPF falls below lower threshold
                    allocatedFramesPerProcess.set(j, allocatedFramesPerProcess.get(j) - 1);
                }
            }
        }

        // Step 3: Simulate with adjusted frame allocation
        for (int j = 0; j < processCount; j++) {
            totalPageFaults += simulateLRU(pageReferencesPerProcess.get(j), allocatedFramesPerProcess.get(j));
        }

        return totalPageFaults;
    }

    // Helper method to simulate LRU for a given list of page references and allocated frames
    private int simulateLRU(List<Integer> pageReferences, int framesPerProcess) {
        LRUSimulator lruSimulator = new LRUSimulator(pageReferences, framesPerProcess);
        return lruSimulator.simulateLRU();
    }

    // Helper method to calculate the total number of pages across all processes
    private int allPagesCount() {
        return pageReferencesPerProcess.stream().mapToInt(List::size).sum();
    }
}
