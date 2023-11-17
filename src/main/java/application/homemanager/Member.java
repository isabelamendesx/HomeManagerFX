package application.homemanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Member implements Serializable {
    private String name;
    private List<String> currentTasks;

    public Member(String name) {
        this.name = name;
        this.currentTasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member {" +
                "name ='" + name + '\'' +
                '}';
    }
}
