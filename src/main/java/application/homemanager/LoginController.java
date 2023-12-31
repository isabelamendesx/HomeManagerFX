package application.homemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class LoginController{

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;
    @FXML
    private Label lbResult;

    private HomePageController homePageController;

    public void setHomePageController(HomePageController homePageController) {
        this.homePageController = homePageController;
    }

    public void onBtnLoginClick(){
            String enteredUsername = txtUsername.getText();
            char[] enteredPassword = txtPassword.getText().toCharArray();  // Usando getPassword para obter a senha como um char[]

            // Cria uma instância de Home com as credenciais fornecidas
            Home user = HomeRepository.usernameAlreadyExists(enteredUsername);

            // Verifica a autenticação usando o método verificarSenha
            if (user != null && user.verificarSenha(enteredPassword)) {

                Session.getInstance().setCurrentUser(user);

                if (homePageController != null) {
                    homePageController.showAllTasks();
                    homePageController.addMembersButtons();
                }

                Main.changeScreen("homePage");
            } else {
                lbResult.setText("Usuário não encontrado ou senha incorreta");
            }
    }

    public void onBtnSignClick(ActionEvent event){
        Main.changeScreen("registerPage");
    }

    public void onKeyReleased(){
        boolean login;

        login = (txtUsername.getText().isEmpty() | txtPassword.getText().isEmpty());
        btnLogin.setDisable(login);

    }

}
