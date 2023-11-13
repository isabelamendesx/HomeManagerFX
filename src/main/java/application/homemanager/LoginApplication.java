package application.homemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApplication extends Application {
    @Override
    public void start(Stage primaryStage){
        try{
            Parent p = FXMLLoader.load(getClass().getResource("/application/homemanager/login.fxml"));

            Scene cena = new Scene(p);

            primaryStage.setScene(cena);

            primaryStage.show();

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        adicionarUsuarios();
        launch();
    }

    private static void adicionarUsuarios(){
        HomeRepository.addHome(new Home("matheusvidal", "teamoisa".toCharArray()));
        HomeRepository.addHome(new Home("isamendes", "astlpb92".toCharArray()));

    }

}