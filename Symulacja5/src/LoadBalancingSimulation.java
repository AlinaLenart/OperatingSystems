import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LoadBalancingSimulation {
    private static final Random random = new Random();

    public static SimulationResult runSimulation(Processor[] processors, int numTasks, int deltaT, double p, double r, int maxArrivalTime, int maxExecutionTime,  int maxTries, Strategy strategy) {
        Statistics stats = new Statistics();
        List<Task> tasksList = new ArrayList<>();

        for (int i = 0; i < numTasks; i++) {
            // zadania o wymaganiach 1% - 6%, czasie trwania 1-maxExecutionTime, arrivalTime: 0-maxArrivalTime
            Task task = new Task(random.nextDouble() * 0.05 + 0.01, random.nextInt(maxExecutionTime) + 1, random.nextInt(maxArrivalTime));
            tasksList.add(task);
        }

        double totalLoad = 0;
        int timeSteps = 0;
        int maxTime = maxExecutionTime + maxArrivalTime + 1;
        double[] loadHistory = new double[maxTime / deltaT];
        int currentTime = 0;
        boolean newTaskAssigned = false;

        while (!tasksList.isEmpty()) {
            if (newTaskAssigned) {
                for (Processor processor : processors) {
                    processor.updateTasks(deltaT);
                }
                newTaskAssigned = false;
            }

            Iterator<Task> iterator = tasksList.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getArrivalTime() <= currentTime) {
                    strategy.assignTask(task, processors, p, r, maxTries, stats);
                    iterator.remove();
                    newTaskAssigned = true;
                }
            }

            double loadSum = 0;
            for (Processor processor : processors) {
                loadSum += processor.getCurrentLoad();
            }

            double averageLoad = loadSum / processors.length;
            totalLoad += averageLoad;
            loadHistory[timeSteps] = averageLoad;
            timeSteps++;

            currentTime += deltaT;
        }

        double meanLoad = totalLoad / timeSteps;
        double variance = 0;
        for (double load : loadHistory) {
            variance += Math.pow(load - meanLoad, 2);
        }
        double stddev = Math.sqrt(variance / timeSteps);

        return new SimulationResult(meanLoad, stddev, stats);
    }

    public interface Strategy {
        void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats);
    }
}


    /* for (int t = 0; t < maxArrivalTime + maxArrivalTime; t += deltaT) {
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

        return new SimulationResult(meanLoad, stddev, stats, allTasksCompleted);*/

