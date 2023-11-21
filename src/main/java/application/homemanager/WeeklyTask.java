package application.homemanager;

public class WeeklyTask extends Task{

    public WeeklyTask(String taskName) {
        super(taskName);
    }


    @Override
    public String toString() {
        return "WeeklyTask = " + getTaskName();
    }
}
