import java.util.List;

public class Main {
    public static void main(String[] args) {
        int totalReferences = 50;
        List<Integer> pageReferences = Generator.generatePageReferences(totalReferences);
        generatedPrint(totalReferences, pageReferences);

        int ramSize = 5;
        Algorithms alg = new Algorithms(pageReferences, ramSize);
        alg.FIFO();







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