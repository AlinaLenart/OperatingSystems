import java.util.ArrayList;
import java.util.List;

public class EqualAllocation {
    private final List<List<Integer>> pageReferencesPerProcess;
    private final int ramSize;
    private final int processCount;
    public EqualAllocation(List<List<Integer>> pageReferencesPerProcess, int ramSize) {
        this.pageReferencesPerProcess = new ArrayList<>(pageReferencesPerProcess);
        this.ramSize = ramSize;
        this.processCount = pageReferencesPerProcess.size();
    }

    public int simulate() {
        int framesPerProcess = ramSize / processCount;
        int totalPageFaults = 0;

        for (List<Integer> pageReferences : pageReferencesPerProcess) {
            LRUSimulator lrusimulator = new LRUSimulator(pageReferences, framesPerProcess);
            int pageFaults = lrusimulator.simulateLRU();
            totalPageFaults += pageFaults;
            System.out.println("Proces nr "+ (pageReferencesPerProcess.indexOf(pageReferences) + 1) + " dostał ramek: "+ framesPerProcess + " wygenerowal bledow: "+ pageFaults);

        }
        return totalPageFaults;
    }





}
