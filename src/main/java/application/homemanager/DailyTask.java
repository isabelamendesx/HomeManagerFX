package application.homemanager;

public class DailyTask extends Task{

    public DailyTask(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        return "DailyTask = " + getTaskName();
    }
}
