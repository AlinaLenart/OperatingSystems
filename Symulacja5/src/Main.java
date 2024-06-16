public class Main {
    public static void main(String[] args) {
        int N = 50; // Liczba procesorów
        int numTasks = 1000; // Liczba zadań do wykonania
        double p = 0.7; // Próg obciążenia
        double r = 0.3; // Minimalny próg obciążenia dla Strategy3
        int maxTries = 3; // Maksymalna liczba prób dla Strategy1
        int maxTime = 1000; // Całkowity czas symulacji
        int deltaT = 1; // Jednostka czasu do obliczania średniego obciążenia

        Processor[] processors = new Processor[N];
        for (int i = 0; i < N; i++) {
            processors[i] = new Processor();
        }

        SimulationResult result1 = LoadBalancingSimulation.runSimulation(processors, numTasks, maxTime, deltaT, p, r, maxTries, new LoadBalancingSimulation.Strategy() {
            @Override
            public void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats) {
                Strategy1.assignTask(task, processors, p, maxTries, stats);
            }
        });
        printResults("Strategy1", result1);

        // Resetowanie procesorów
        for (int i = 0; i < N; i++) {
            processors[i] = new Processor();
        }

        SimulationResult result2 = LoadBalancingSimulation.runSimulation(processors, numTasks, maxTime, deltaT, p, r, maxTries, new LoadBalancingSimulation.Strategy() {
            @Override
            public void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats) {
                Strategy2.assignTask(task, processors, p, stats);
            }
        });
        printResults("Strategy2", result2);

        // Resetowanie procesorów
        for (int i = 0; i < N; i++) {
            processors[i] = new Processor();
        }

        SimulationResult result3 = LoadBalancingSimulation.runSimulation(processors, numTasks, maxTime, deltaT, p, r, maxTries, new LoadBalancingSimulation.Strategy() {
            @Override
            public void assignTask(Task task, Processor[] processors, double p, double r, int maxTries, Statistics stats) {
                Strategy3.assignTask(task, processors, p, r, stats);
            }
        });
        printResults("Strategy3", result3);
    }

    private static void printResults(String strategyName, SimulationResult result) {
        System.out.println(strategyName + " Results:");
        System.out.println("Średnie obciążenie procesorów: " + result.getMeanLoad());
        System.out.println("Średnie odchylenie od wartości: " + result.getStddev());
        System.out.println("Liczba zapytań o obciążenie: " + result.getStatistics().getQueries());
        System.out.println("Liczba migracji procesów: " + result.getStatistics().getMigrations());
        System.out.println("Wszystkie zadania zakończone: " + result.areAllTasksCompleted());
        System.out.println();
    }
}
