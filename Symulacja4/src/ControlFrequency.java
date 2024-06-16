import java.util.*;

public class ControlFrequency {
    private final List<List<Integer>> pageReferences;
    private final int ramSize;
    private final int processCount;
    private final int pagesVariety;
    private final int timeWindow; // okno czasowe dla pomiaru PPF Δt
    private final double lowerThreshold; // L - dolny prog czestosci bledow strony
    private final double upperThreshold; // U - gorny prog czestosci bledow strony
    public ControlFrequency(List<List<Integer>> pageReferences, int ramSize, int timeWindow, double lowerThreshold, double upperThreshold, int pagesVariety) {
        this.pageReferences = new ArrayList<>(pageReferences);
        this.ramSize = ramSize;
        this.processCount = pageReferences.size();
        this.timeWindow = timeWindow;
        this.lowerThreshold = lowerThreshold;
        this.upperThreshold = upperThreshold;
        this.pagesVariety = pagesVariety;
    }

    public int simulate() {
        int totalPageFaults = 0;
        List<Integer> dedicatedFramesPerProcess = new ArrayList<>(Collections.nCopies(processCount, 0));
        List<Integer> totalPageFaultsPerProcess = new ArrayList<>(Collections.nCopies(processCount, 0));

        for (int i = 0; i < processCount; i++) { //wyznaczenie wedlug przydzialu proporcjonalnego
            List<Integer> pageReferences = this.pageReferences.get(i);
            int processVariety = Collections.max(pageReferences) - Collections.min(pageReferences) + 1;
            int framesPerProcess = (int) Math.floor((double) processVariety / pagesVariety * ramSize);
            dedicatedFramesPerProcess.set(i, framesPerProcess);
        }

        //petla odpala LRU dla kazdego procesu w aktualnym Δt i dodaje bledy stron
        int totalReferences = pageReferences.getFirst().size();
        for (int t = 0; t < totalReferences; t += timeWindow) {
            List<Integer> currentPageFaultsPerProcess = new ArrayList<>(Collections.nCopies(processCount, 0));

            for (int i = 0; i < processCount; i++) {
                List<Integer> pageReferences = this.pageReferences.get(i).subList(t, Math.min(t + timeWindow, totalReferences));
                int framesPerProcess = dedicatedFramesPerProcess.get(i);
                LRUSimulator lruSimulator = new LRUSimulator(pageReferences, framesPerProcess);
                int pageFaults = lruSimulator.simulateLRU();
                currentPageFaultsPerProcess.set(i, pageFaults);
                totalPageFaultsPerProcess.set(i, totalPageFaultsPerProcess.get(i) + pageFaults);
                totalPageFaults += pageFaults;

            }

            for (int i = 0; i < processCount; i++) {
                double ppf = (double) currentPageFaultsPerProcess.get(i) / timeWindow; //e_i / Δt
                if (ppf > upperThreshold) {
                    dedicatedFramesPerProcess.set(i, dedicatedFramesPerProcess.get(i) + 1); // dodaje ramke
                } else if (ppf < lowerThreshold && dedicatedFramesPerProcess.get(i) >= 1) {
                    dedicatedFramesPerProcess.set(i, dedicatedFramesPerProcess.get(i) - 1); // zabieram
                }
            }
        }

        for (int i = 0; i < processCount; i++) {
            System.out.println("Proces nr " + (i + 1) + " wygenerowal bledow: " + totalPageFaultsPerProcess.get(i));
        }
        return totalPageFaults;
    }
}
