import java.util.ArrayList;
import java.util.List;

public class WSS {
    private final List<List<Integer>> pageReferencesPerProcess;
    private final int ramSize;
    private final int processCount;
    private final int timeWindow; // Okno czasowe dla pomiaru WSS
    private final double WSSCoefficient; // Współczynnik dla WSS

    public WSS(List<List<Integer>> pageReferencesPerProcess, int ramSize, int timeWindow, double WSSCoefficient) {
        this.pageReferencesPerProcess = pageReferencesPerProcess;
        this.ramSize = ramSize;
        this.processCount = pageReferencesPerProcess.size();
        this.timeWindow = timeWindow;
        this.WSSCoefficient = WSSCoefficient;
    }

    // Strategy 4: Working Set Model
    public int workingSetModel() {
        int totalPageFaults = 0;
        List<Integer> allocatedFramesPerProcess = new ArrayList<>(processCount);

        // Step 1: Initial allocation based on WSS
        for (List<Integer> pageReferences : pageReferencesPerProcess) {
            int framesPerProcess = (int) Math.floor(calculateWorkingSetSize(pageReferences) * WSSCoefficient);
            allocatedFramesPerProcess.add(framesPerProcess);
            totalPageFaults += simulateLRU(pageReferences, framesPerProcess);
        }

        // Step 2: Monitor WSS and adjust frame allocation
        for (int i = timeWindow; i < allPagesCount(); i++) {
            for (int j = 0; j < processCount; j++) {
                List<Integer> pageReferences = pageReferencesPerProcess.get(j);
                int pageFaults = simulateLRU(pageReferences, allocatedFramesPerProcess.get(j));
                totalPageFaults += pageFaults;

                if (pageFaults > allocatedFramesPerProcess.get(j)) {
                    // If page faults exceed allocated frames, suspend the process and reallocate frames
                    int suspendedProcessIndex = j;
                    for (int k = 0; k < processCount; k++) {
                        if (k != j && allocatedFramesPerProcess.get(k) > 0) {
                            suspendedProcessIndex = k;
                            break;
                        }
                    }
                    allocatedFramesPerProcess.set(suspendedProcessIndex, allocatedFramesPerProcess.get(suspendedProcessIndex) - 1);
                    allocatedFramesPerProcess.set(j, pageFaults);
                }
            }
        }

        return totalPageFaults;
    }

    // Helper method to simulate LRU for a given list of page references and allocated frames
    private int simulateLRU(List<Integer> pageReferences, int framesPerProcess) {
        LRUSimulator lruSimulator = new LRUSimulator(pageReferences, framesPerProcess);
        return lruSimulator.simulateLRU();
    }

    // Helper method to calculate the working set size
    private double calculateWorkingSetSize(List<Integer> pageReferences) {
        int distinctPages = (int) pageReferences.stream().distinct().count();
        return Math.min(distinctPages, ramSize);
    }

    // Helper method to calculate the total number of pages across all processes
    private int allPagesCount() {
        return pageReferencesPerProcess.stream().mapToInt(List::size).sum();
    }
}
