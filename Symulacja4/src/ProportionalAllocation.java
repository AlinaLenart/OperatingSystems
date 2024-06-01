import java.util.List;

public class ProportionalAllocation {
    private final List<List<Integer>> pageReferencesPerProcess;
    private final int ramSize;
    private int allPagesCount;
    public ProportionalAllocation(List<List<Integer>> pageReferencesPerProcess, int ramSize) {
        this.pageReferencesPerProcess = pageReferencesPerProcess;
        this.ramSize = ramSize;
        this.allPagesCount = 0;

        for(List<Integer> pageReferences : pageReferencesPerProcess) {
            allPagesCount += pageReferences.size();
        }
    }

    // Strategy 2: Proportional Allocation
    public int simulate() {
        int totalPageFaults = 0;

        for (List<Integer> pageReferences : pageReferencesPerProcess) {

            int framesPerProcess = (int) Math.floor((double) pageReferences.size() / allPagesCount * ramSize); //liczy podloge
            LRUSimulator lruSimulator = new LRUSimulator(pageReferences, framesPerProcess);
            totalPageFaults += lruSimulator.simulateLRU();
        }

        return totalPageFaults;
    }
}
