package application.homemanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

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

    public void onBtnLoginClick(){
            String enteredUsername = txtUsername.getText();
            char[] enteredPassword = txtPassword.getText().toCharArray();  // Usando getPassword para obter a senha como um char[]

            // Cria uma instância de Home com as credenciais fornecidas
            Home user = HomeRepository.procuraUsername(enteredUsername);

            // Verifica a autenticação usando o método verificarSenha
            if (user != null && user.verificarSenha(enteredPassword)) {
                lbResult.setText("Autenticado!");
            } else {
                lbResult.setText("Usuário não encontrado ou senha incorreta");
            }
    }

    public void onBtnSignClick(){
        txtUsername.setText("");
        txtPassword.setText("");
        lbResult.setText("");
        btnSignup.setDisable(true);
        btnLogin.setDisable(true);
    }

    public void onKeyReleased(){
        boolean login;
        boolean signUp;

        login = (txtUsername.getText().isEmpty() | txtPassword.getText().isEmpty());
        btnLogin.setDisable(login);

        signUp = (txtPassword.getText().isEmpty() && txtUsername.getText().isEmpty());
        btnSignup.setDisable(signUp);

    }

}
