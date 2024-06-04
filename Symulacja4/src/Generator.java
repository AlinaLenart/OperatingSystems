import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    public static List<Integer> generatePageReferences(int min, int max, int totalReferences) {
        List<Integer> pageReferences = new ArrayList<>();
        Random random = new Random();

        int maxRepeat = random.nextInt(8) + 10; // zakres [5, 10] dla dłuższych sekwencji lokalności

        for (int i = 0; i < totalReferences; i++) {
            int repeat = random.nextInt(3); // zwiększamy częstotliwość lokalności (zakres [0, 2])
            int pageNumber;
            if (repeat < 2 && !pageReferences.isEmpty() && i > max) {
                // jeśli lokalności, wybierz jedną z poprzednich liczb
                pageNumber = pageReferences.get(i - random.nextInt(max) - 1);
            }
            else {
                pageNumber = random.nextInt(max - min + 1) + min; // zakres odwolan stron [min, max]
            }
            pageReferences.add(pageNumber);
        }
        return pageReferences;
    }
}
