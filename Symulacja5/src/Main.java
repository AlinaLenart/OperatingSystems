import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int N = 50;
        int numTasks = 10000;
        double p = 0.5;             // gorny prog
        double r = 0.1;             // dolny prog
        int maxTries = 10;           // dla strategi 1
        int maxArrivalTime = 500;
        int maxExecutionTime = 100;
        int deltaT = 1;

        Processor[] processors = new Processor[N];
        for (int i = 0; i < N; i++) {
            processors[i] = new Processor();
        }

        List<Task> tasksList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numTasks; i++) {
            // zadania o wymaganiach 1% - 6%, czasie trwania 1-maxExecutionTime, arrivalTime: 0-maxArrivalTime
            Task task = new Task(random.nextDouble() * 0.05 + 0.01, random.nextInt(maxExecutionTime) + 1, random.nextInt(maxArrivalTime));
            tasksList.add(task);
        }

        SimulationResult result1 = LoadBalancingSimulation.runSimulation(tasksList, processors, numTasks, deltaT, p, r, maxArrivalTime, maxExecutionTime, maxTries, new LoadBalancingSimulation.Strategy() {
            @Override
            public void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats) {
                Strategy1.assignTask(task, processors, p, maxTries, stats);
            }
        });
        printResults("Strategy1", result1);


        for (int i = 0; i < N; i++) {
            processors[i] = new Processor();
        }

        SimulationResult result2 = LoadBalancingSimulation.runSimulation(tasksList, processors, numTasks, deltaT, p, r, maxArrivalTime, maxExecutionTime, maxTries, new LoadBalancingSimulation.Strategy() {
            @Override
            public void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats) {
                Strategy2.assignTask(task, processors, p, stats);
            }
        });
        printResults("Strategy2", result2);


        for (int i = 0; i < N; i++) {
            processors[i] = new Processor();
        }

        SimulationResult result3 = LoadBalancingSimulation.runSimulation(tasksList, processors, numTasks, deltaT, p, r, maxArrivalTime, maxExecutionTime, maxTries, new LoadBalancingSimulation.Strategy() {
            @Override
            public void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats) {
                Strategy3.assignTask(task, processors, p, r, stats);
            }
        });
        printResults("Strategy3", result3);
    }

    private static void printResults(String strategyName, SimulationResult result) {
        System.out.println(strategyName + " --> Wyniki:");
        System.out.println("Srednie obciążenie procesorow: " + result.getMeanLoad());
        System.out.println("Srednie odchylenie od wartosci: " + result.getStddev());
        System.out.println("Liczba zapytan o obciazenie: " + result.getStatistics().getQueries());
        System.out.println("Liczba migracji procesow: " + result.getStatistics().getMigrations());
        System.out.println("Ilosc przeciazen procesora: " + result.getStatistics().getOverloads());
        System.out.println();
    }
}
