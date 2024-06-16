import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Strategy3 {
    private static final Random random = new Random();

    public static void assignTask(Task task, Processor[] processors, double p, double r, Statistics stats) {
        Processor currentProcessor = processors[random.nextInt(processors.length)];

        if (currentProcessor.getCurrentLoad() > p) {
            List<Integer> numbers = randomGenerator(processors);
            for (int index : numbers) {
                stats.incrementQueries();
                Processor randomProcessor = processors[index];
                if (randomProcessor.getCurrentLoad() < p) {
                    randomProcessor.addTask(task);
                    stats.incrementMigrations();
                    return;
                }
            }

        } else {
            currentProcessor.addTask(task);
        }

        for (Processor processor : processors) {

            if (processor.getCurrentLoad() < r) {

                List<Integer> numbers2 = randomGenerator(processors);

                for (int index : numbers2) {

                    Processor randomProcessor = processors[index];
                    stats.incrementQueries();

                    if (randomProcessor.getCurrentLoad() > p) {

                        Task transferredTask = randomProcessor.removeTask(); //przenosze jeden - ostatni

                        if (transferredTask != null) {

                            processor.addTask(transferredTask);
                            stats.incrementMigrations();
                        }
                    }
                }
            }
        }
    }
    private static List<Integer> randomGenerator(Processor[] processors){
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < processors.length; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers, new Random());

        return numbers;
    }
}
