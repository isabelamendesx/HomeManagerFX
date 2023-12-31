package application.homemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HomeRepository {

    private static List<Home> homeList = new ArrayList<>();
    private static final String FILE_PATH = "user_data.dat";

    public static void printHomeList(){
        //homeList.forEach(System.out::println);
        homeList.forEach(Home::printTasks);
    }

    static {
        loadUserData();
        homeList.forEach(Home::distribuirTarefasSemanais);
        homeList.forEach(Home::distribuirTarefasDiarias);
        printHomeList();
    }
    public static void addHome(Home home){
        homeList.add(home);
        saveUserData();
    }

    public static Home usernameAlreadyExists(String username) {
        for (Home home : homeList) {
            if (home.getUsername().equals(username)) {
                return home;
            }
        }
        return null; // Retorna null se o usuário não for encontrado
    }

    public static void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(homeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                homeList = (List<Home>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("NÃO ABRIU O ARQUIVO BINARIO");
            homeList = new ArrayList<>();
        }
    }

    public static List<DailyTask> defaultDailyTasks(){
        List<DailyTask> defaultDailyTasks = new ArrayList<>();
        defaultDailyTasks.add(new DailyTask("Varrer a casa"));
        defaultDailyTasks.add(new DailyTask("Lavar a louça"));
        defaultDailyTasks.add(new DailyTask("Arrumar as camas"));
        defaultDailyTasks.add(new DailyTask("Fazer almoço"));
        defaultDailyTasks.add(new DailyTask("Alimentar o cachorro"));
        defaultDailyTasks.add(new DailyTask("Limpar superfícies"));
        defaultDailyTasks.add(new DailyTask("Levar o lixo"));
        defaultDailyTasks.add(new DailyTask("Regar as plantas"));
        return defaultDailyTasks;
    }

    public static List<WeeklyTask> defaultWeeklyTasks(){
        List<WeeklyTask> defaultWeeklyTasks = new ArrayList<>();
        defaultWeeklyTasks.add(new WeeklyTask("Lavar os banheiros"));
        defaultWeeklyTasks.add(new WeeklyTask("Lavar roupas"));
        defaultWeeklyTasks.add(new WeeklyTask("Limpar fogão"));
        defaultWeeklyTasks.add(new WeeklyTask("Limpar geladeira"));
        defaultWeeklyTasks.add(new WeeklyTask("Trocar lençóis"));
        defaultWeeklyTasks.add(new WeeklyTask("Trocar toalhas"));
        defaultWeeklyTasks.add(new WeeklyTask("Dar banho no cachorro"));
        defaultWeeklyTasks.add(new WeeklyTask("Fazer as compras"));
        defaultWeeklyTasks.add(new WeeklyTask("Ir ao açougue"));
        return defaultWeeklyTasks;
    }

}
