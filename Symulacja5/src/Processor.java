import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Processor {
    private List<Task> tasks; // Lista zadań przypisanych do procesora

    public Processor() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public double getCurrentLoad() {
        double totalLoad = 0;
        for (Task task : tasks) {
            totalLoad += task.getRequiredLoad();
        }
        return totalLoad;
    }

    public void updateTasks() {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            task.decrementExecutionTime();
            if (task.isCompleted()) {
                iterator.remove();
            }
        }
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public Task removeTask() {
        if (tasks.isEmpty()) {
            return null;
        }
        return tasks.remove(tasks.size() - 1);
    }
}
