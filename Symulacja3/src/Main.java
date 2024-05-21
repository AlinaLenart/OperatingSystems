import java.util.List;

public class Main {
    public static void main(String[] args) {
        int totalReferences = 10000;
        List<Integer> pageReferences = Generator.generatePageReferences(totalReferences);
        generatedPrint(totalReferences, pageReferences);

        int ramSize = 7;

        Algorithms alg = new Algorithms(pageReferences, ramSize);

        int fifo = alg.FIFO();
        int rand = alg.RAND();
        int opt = alg.OPT();
        int lru = alg.LRU();
        int approxlru = alg.approximatedLRU();

        System.out.printf("%-15s %-15s %10s \n", "Algorytm", "Bledy strony", "Procent");
        System.out.printf("%-15s %-15d %8.2f%% \n","FIFO", fifo, (double) fifo / totalReferences * 100);
        System.out.printf("%-15s %-15d %8.2f%% \n","RAND", rand, (double) rand / totalReferences * 100);
        System.out.printf("%-15s %-15d %8.2f%% \n","OPT", opt, (double) opt / totalReferences * 100);
        System.out.printf("%-15s %-15d %8.2f%% \n","LRU", lru, (double) lru / totalReferences * 100);
        System.out.printf("%-15s %-15d %8.2f%% \n","approxLRU", approxlru, (double) approxlru / totalReferences * 100);



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