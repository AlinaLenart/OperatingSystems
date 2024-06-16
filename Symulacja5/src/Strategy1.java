import java.util.Random;

public class Strategy1 {
    private static final Random random = new Random();

    public static void assignTask(Task task, Processor[] processors, double p, int maxTries, Statistics stats) {
        Processor currentProcessor = processors[random.nextInt(processors.length)];

        for (int i = 0; i < maxTries; i++) {
            stats.incrementQueries();
            Processor randomProcessor = processors[random.nextInt(processors.length)];
            if (randomProcessor.getCurrentLoad() < p) {
                randomProcessor.addTask(task);
                stats.incrementMigrations();
                return;
            }
        }

        currentProcessor.addTask(task); //nie znalazlam mniej obciazonego
    }
}
