import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    public static List<Integer> generatePageReferences(int totalReferences) {
        List<Integer> pageReferences = new ArrayList<>();
        Random random = new Random();

        int maxRepeat = random.nextInt(6) + 5; // zakres [5, 10] dla dłuższych sekwencji lokalności

        for (int i = 0; i < totalReferences; i++) {
            int repeat = random.nextInt(3); // zwiększamy częstotliwość lokalności (zakres [0, 2])
            int pageNumber;
            if (repeat < 2 && !pageReferences.isEmpty() && i > 15) {
                // jeśli lokalności, wybierz jedną z poprzednich liczb
                pageNumber = pageReferences.get(i - random.nextInt(15) - 1);
            } else {
                pageNumber = random.nextInt(15) + 1; // zakres odwolan stron [1, 15]
            }
            pageReferences.add(pageNumber);
        }
        return pageReferences;
    }
}
