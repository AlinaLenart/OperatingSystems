import java.util.List;

public class EqualAllocation {
    private final List<List<Integer>> pageReferencesPerProcess;
    private final int ramSize;
    private final int processCount;
    public EqualAllocation(List<List<Integer>> pageReferencesPerProcess, int ramSize) {
        this.pageReferencesPerProcess = pageReferencesPerProcess;
        this.ramSize = ramSize;
        this.processCount = pageReferencesPerProcess.size();
    }

    // Strategy 1: Equal Allocation
    public int simulate() {
        int framesPerProcess = ramSize / processCount;
        int totalPageFaults = 0;

        for (List<Integer> pageReferences : pageReferencesPerProcess) {
            LRUSimulator lrusimulator = new LRUSimulator(pageReferences, framesPerProcess);
            totalPageFaults += lrusimulator.simulateLRU();
        }

        return totalPageFaults;
    }





}
