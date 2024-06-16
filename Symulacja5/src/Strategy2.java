import java.util.Random;

public class Strategy2 {
    private static final Random random = new Random();

    public static void assignTask(Task task, Processor[] processors, double p, Statistics stats) {
        Processor currentProcessor = processors[random.nextInt(processors.length)];

        if (currentProcessor.getCurrentLoad() > p) {
            boolean found = false;
            for (Processor processor : processors) {
                stats.incrementQueries();
                if (processor.getCurrentLoad() < p) {
                    processor.addTask(task);
                    stats.incrementMigrations();
                    found = true;
                    break;
                }
            }
            if (!found) {
                currentProcessor.addTask(task);
            }
        } else {
            currentProcessor.addTask(task);
        }
    }
}
