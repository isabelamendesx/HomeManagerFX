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
    private static Scene testeScene;




    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        primaryStage.setTitle("HomeManager");

        // Carregar o FXML do LoginController
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/application/homemanager/login.fxml"));
        Parent rootLogin = loaderLogin.load();
        LoginController loginController = loaderLogin.getController();

        // Carregar o FXML do TesteController
        FXMLLoader loaderTeste = new FXMLLoader(getClass().getResource("/application/homemanager/homePage.fxml"));
        Parent rootTeste = loaderTeste.load();
        HomePageController homePageController = loaderTeste.getController();

        loginController.setTesteController(homePageController);

        // Configurar as cenas
        loginScene = new Scene(rootLogin, 715, 485);
        testeScene = new Scene(rootTeste, 715, 485);


        /*Parent fxmlLoginPage = FXMLLoader.load(getClass().getResource("/application/homemanager/login.fxml"));
        loginScene = new Scene(fxmlLoginPage,715,485);*/

        Parent fxmlRegisterPage = FXMLLoader.load(getClass().getResource("/application/homemanager/cadastro.fxml"));
        registerScene = new Scene(fxmlRegisterPage, 715, 485);

        Parent fxmlTaskChooserPage = FXMLLoader.load(getClass().getResource("/application/homemanager/taskChooser.fxml"));
        taskChooserScene = new Scene(fxmlTaskChooserPage, 600, 400);

        /*Parent fxmlTeste = FXMLLoader.load(getClass().getResource("/application/homemanager/homePage.fxml"));
        testeScene = new Scene(fxmlTeste, 715, 485);*/

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
            case "testePage":{
                stage.setScene(testeScene);
                break;
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }


}