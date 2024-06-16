import java.util.Random;

public class Strategy3 {
    private static final Random random = new Random();

    public static void assignTask(Task task, Processor[] processors, double p, double r, Statistics stats) {
        Processor currentProcessor = processors[random.nextInt(processors.length)];

        if (currentProcessor.getCurrentLoad() > p) {
            while (true) {
                stats.incrementQueries();
                Processor randomProcessor = processors[random.nextInt(processors.length)];
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
                Processor randomProcessor = processors[random.nextInt(processors.length)];
                stats.incrementQueries();
                if (randomProcessor.getCurrentLoad() > p) {
                    Task transferredTask = randomProcessor.removeTask();
                    if (transferredTask != null) {
                        processor.addTask(transferredTask);
                        stats.incrementMigrations();
                    }
                }
            }
        }
    }
}
