package application.homemanager;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {

    private static List<Home> homeList = new ArrayList<>();

    public static void addHome(Home home){
        homeList.add(home);
    }
    
    public static Home procuraUsername(String username) {
        for (Home home : homeList) {
            if (home.getUsername().equals(username)) {
                return home;
            }
        }
        return null; // Retorna null se o usuário não for encontrado
    }

}
