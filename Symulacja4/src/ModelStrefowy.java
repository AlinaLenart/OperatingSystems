import java.util.*;

public class ModelStrefowy {
    private final List<List<Integer>> pageReferencesPerProcess;
    private final int ramSize;
    private final int processCount;
    private final double timeWindow; // Współczynnik dla okna czasowego Δt

    public ModelStrefowy(List<List<Integer>> pageReferencesPerProcess, int ramSize, double timeWindow) {
        this.pageReferencesPerProcess = new ArrayList<>(pageReferencesPerProcess);
        this.ramSize = ramSize;
        this.processCount = pageReferencesPerProcess.size();
        this.timeWindow = timeWindow;
    }

    public int simulate() {
        int totalPageFaults = 0;
        int canceledProcesses = 0;
        List<Integer> totalPageFaultsPerProcess = new ArrayList<>(Collections.nCopies(processCount, 0));

        // Initialize working sets and references for each process
        List<Deque<Integer>> workingSets = new ArrayList<>();
        List<Deque<Integer>> references = new ArrayList<>();

        for (List<Integer> process : pageReferencesPerProcess) {
            workingSets.add(new ArrayDeque<>());
            references.add(new ArrayDeque<>(process));
        }

        while (true) {
            int D = 0;
            List<Integer> currentWSS = new ArrayList<>();

            // Calculate WSS for each process
            for (int i = 0; i < processCount; i++) {
                Deque<Integer> workingSet = workingSets.get(i);
                Deque<Integer> reference = references.get(i);
                Set<Integer> pagesInWindow = new HashSet<>();

                for (int j = 0; j < timeWindow; j++) {
                    if (reference.isEmpty()) break;
                    int page = reference.poll();
                    workingSet.add(page);
                    pagesInWindow.add(page);

                    // Maintain the working set within the time window
                    if (workingSet.size() > timeWindow) {
                        workingSet.poll();
                    }
                }

                int wssSize = pagesInWindow.size();
                currentWSS.add(wssSize);
                D += wssSize;
            }

            // Check if we have processed all references
            if (currentWSS.stream().allMatch(wss -> wss == 0)) {
                break;
            }

            // Check if D exceeds available RAM size
            if (D <= ramSize) {
                for (int i = 0; i < processCount; i++) {
                    int framesPerProcess = currentWSS.get(i);
                    if (framesPerProcess > 0) {
                        Deque<Integer> workingSet = workingSets.get(i);
                        List<Integer> currentReferences = new ArrayList<>(workingSet);
                        LRUSimulator lruSimulator = new LRUSimulator(currentReferences, framesPerProcess);
                        totalPageFaults += lruSimulator.simulateLRU();
                        totalPageFaultsPerProcess.set(i, totalPageFaultsPerProcess.get(i) + lruSimulator.simulateLRU());
                    }
                }
            } else {
                // Suspend the process with the largest WSS
                int maxWSSIndex = currentWSS.indexOf(Collections.max(currentWSS));
                canceledProcesses++;
                currentWSS.set(maxWSSIndex, 0);

                // Redistribute frames proportionally to the remaining processes
                int remainingWSS = currentWSS.stream().mapToInt(Integer::intValue).sum();
                for (int i = 0; i < processCount; i++) {
                    if (i != maxWSSIndex && currentWSS.get(i) > 0) {
                        int framesPerProcess = (int) Math.floor((double) currentWSS.get(i) / remainingWSS * ramSize);
                        Deque<Integer> workingSet = workingSets.get(i);
                        List<Integer> currentReferences = new ArrayList<>(workingSet);
                        LRUSimulator lruSimulator = new LRUSimulator(currentReferences, framesPerProcess);
                        totalPageFaults += lruSimulator.simulateLRU();
                        totalPageFaultsPerProcess.set(i, totalPageFaultsPerProcess.get(i) + lruSimulator.simulateLRU());
                    }
                }
            }
        }

        // Print total page faults per process at the end of simulation
        for (int i = 0; i < processCount; i++) {
            System.out.println("Proces nr " + (i + 1) + " wygenerowal calkowicie bledow: " + totalPageFaultsPerProcess.get(i));
        }

        System.out.println("Total canceled processes: " + canceledProcesses);
        return totalPageFaults;
    }



}
