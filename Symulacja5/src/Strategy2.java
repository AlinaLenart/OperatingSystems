import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Strategy2 { //default
    private static final Random random = new Random();

    public static void assignTask(Task task, Processor[] processors, double p, Statistics stats) {
        Processor currentProcessor = processors[random.nextInt(processors.length)];

        if (currentProcessor.getCurrentLoad() > p) {

            for (Processor processor : processors) { //zeby przeeszlo po kazdym max raz
                stats.incrementQueries();
                if (processor.getCurrentLoad() < p) {
                    processor.addTask(task);
                    stats.incrementMigrations();

                    break;
                }
            }

        }
        currentProcessor.addTask(task);

    }

    private static List<Integer> randomGenerator(Processor[] processors){
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= processors.length; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers, new Random());

        return numbers;
    }
}
