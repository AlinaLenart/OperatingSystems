import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    public static List<Integer> generatePageReferences(int totalReferences) {
        List<Integer> pageReferences = new ArrayList<>();
        Random random = new Random();

        int maxRepeat = random.nextInt(6); // zakres [0, 5]

        for (int i = 0; i < totalReferences; i++) {
            int repeat = random.nextInt(11); // zakres [0, 10]
            int pageNumber;
            if (repeat < maxRepeat && !pageReferences.isEmpty() && i > 6) {
                //jeśli lokalności, wybierz jedną z poprzednich liczb
                pageNumber = pageReferences.get(Math.abs(i - random.nextInt(6) - 1));
            } else {
                pageNumber = random.nextInt(15) + 1; //zakres odwolan stron [1, 15]
            }
            pageReferences.add(pageNumber);
        }
        return pageReferences;
    }
}
