import java.util.List;

public class Main {
    public static void main(String[] args) {
        int totalReferences = 10000;
        List<Integer> pageReferences = Generator.generatePageReferences(totalReferences);
        //generatedPrint(totalReferences, pageReferences);
        //List<Integer> pageReferences = List.of(1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5);

        int ramSize = 7;

        Algorithms alg = new Algorithms(pageReferences, ramSize);
        alg.FIFO();
        alg.RAND();
        alg.OPT();
        alg.LRU();
        alg.approximatedLRU();



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