package application.homemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {

    private static Stage stage;
    // SCENE = ARMAZENAR TELAS
    private static Scene loginScene;
    private static Scene registerScene;
    private static Scene taskChooserScene;
    private static Scene dashboardScene;


    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        primaryStage.setTitle("HomeManager");

        Parent fxmlLoginPage = FXMLLoader.load(getClass().getResource("/application/homemanager/login.fxml"));
        loginScene = new Scene(fxmlLoginPage,715,485);

        Parent fxmlRegisterPage = FXMLLoader.load(getClass().getResource("/application/homemanager/cadastro.fxml"));
        registerScene = new Scene(fxmlRegisterPage, 715, 485);

        Parent fxmlTaskChooserPage = FXMLLoader.load(getClass().getResource("/application/homemanager/taskChooser.fxml"));
        taskChooserScene = new Scene(fxmlTaskChooserPage, 600, 400);

        Parent fxmlDashboard = FXMLLoader.load(getClass().getResource("/application/homemanager/dashboard.fxml"));
        dashboardScene = new Scene(fxmlDashboard, 715, 485);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void changeScreen(String scene){
        switch (scene){
            case "loginPage":{
                stage.setScene(loginScene);
                break;
            }
            case "registerPage":{
                stage.setScene(registerScene);
                break;
            }
            case "taskChooserPage":{
                stage.setScene(taskChooserScene);
                break;
            }
            case "dashboardPage":{
                stage.setScene(dashboardScene);
                break;
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }


}