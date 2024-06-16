import java.util.Random;

public class LoadBalancingSimulation {
    private static final Random random = new Random();

    public static SimulationResult runSimulation(Processor[] processors, int numTasks, int maxTime, int deltaT, double p, double r, int maxTries, Strategy strategy) {
        Statistics stats = new Statistics();

        // Generowanie zadań
        for (int i = 0; i < numTasks; i++) {
            Task task = new Task(random.nextDouble() * 0.05 + 0.01, random.nextInt(100) + 1); // Zadania o wymaganiach 1% - 6% i czasie trwania 1 - 100 jednostek czasu
            strategy.assignTask(task, processors, p, r, maxTries, stats);
        }

        double totalLoad = 0;
        int timeSteps = 0;
        double[] loadHistory = new double[maxTime / deltaT];

        for (int t = 0; t < maxTime; t += deltaT) {
            // Aktualizacja zadań na procesorach
            for (Processor processor : processors) {
                processor.updateTasks();
            }

            // Obliczanie średniego obciążenia
            double loadSum = 0;
            for (Processor processor : processors) {
                loadSum += processor.getCurrentLoad();
            }
            double averageLoad = loadSum / processors.length;
            totalLoad += averageLoad;
            loadHistory[timeSteps] = averageLoad;
            timeSteps++;
        }

        double meanLoad = totalLoad / timeSteps;
        double variance = 0;
        for (double load : loadHistory) {
            variance += Math.pow(load - meanLoad, 2);
        }
        double stddev = Math.sqrt(variance / timeSteps);

        // Sprawdzenie, czy wszystkie zadania zostały zakończone
        boolean allTasksCompleted = true;
        for (Processor processor : processors) {
            if (processor.getTaskCount() > 0) {
                allTasksCompleted = false;
                break;
            }
        }

        return new SimulationResult(meanLoad, stddev, stats, allTasksCompleted);
    }

    public interface Strategy {
        void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats);
    }
}
