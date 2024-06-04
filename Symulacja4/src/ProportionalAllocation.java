import java.util.List;
import java.util.*;

public class ProportionalAllocation {
    private final List<List<Integer>> pageReferencesPerProcess;
    private final int ramSize;
    private final int pagesVariety;
    private int allPagesCount;
    public ProportionalAllocation(List<List<Integer>> pageReferencesPerProcess, int ramSize, int pagesVariety) {
        this.pageReferencesPerProcess = new ArrayList<>(pageReferencesPerProcess);
        this.ramSize = ramSize;
        this.pagesVariety = pagesVariety;
    }

    // Strategy 2: Proportional Allocation
    public int simulate() {
        int totalPageFaults = 0;
        int[] maxPageFaults = new int[2];

        for (List<Integer> pageReferences : pageReferencesPerProcess) {
            int processVariety = Collections.max(pageReferences) - Collections.min(pageReferences) + 1;
            int framesPerProcess = (int) Math.floor((double) processVariety / pagesVariety * ramSize); //liczy podloge
            LRUSimulator lruSimulator = new LRUSimulator(pageReferences, framesPerProcess);
            int pageFaults = lruSimulator.simulateLRU();
            totalPageFaults += pageFaults;
            System.out.println("Proces nr "+ (pageReferencesPerProcess.indexOf(pageReferences) + 1) + " dostał ramek: "+ framesPerProcess + " wygenerowal bledow: "+ pageFaults);

        }
        return totalPageFaults;
    }
}
