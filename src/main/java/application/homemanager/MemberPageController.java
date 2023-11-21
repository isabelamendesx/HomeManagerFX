package application.homemanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MemberPageController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML private VBox vbBtnMembers;

    @FXML private Button btnHome;

    @FXML private Label lbHello;

    @FXML private HBox hbWeeklyTasks;

    public void onBtnLogoutCLick(){
        Main.changeScreen("loginPage");
    }

    public void showMemberHello(Member member){
        lbHello.setText("Hello, " + member.getName() + "!");
    }

    public void showMemberWTasks(Member member){
        List <WeeklyTask> Weeklytasks = member.getTarefasSemanais();

        hbWeeklyTasks.getChildren().clear();

        for(WeeklyTask weeklytasks : Weeklytasks){
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getStyleClass().add("task-anchor");
            hbWeeklyTasks.getChildren().add(anchorPane);
        }

        hbWeeklyTasks.setSpacing(5);

    }

    public void setMemberInfo(Member member){
        addMembersButtons();
        showMemberHello(member);
        showMemberWTasks(member);
    }


    public void addMembersButtons() {
        List<Member> membersList = Session.getInstance().getCurrentUser().getMembersList();

        vbBtnMembers.getChildren().clear();

        // Adiciona novos campos TextField conforme a quantidade de membros
        for (Member member : membersList) {
            Button button = new Button(member.getName());
            button.getStyleClass().add("button-member");
            vbBtnMembers.getChildren().add(button);

            button.setOnAction(event -> handleMemberButtonClick(member));
        }
        vbBtnMembers.setAlignment(Pos.TOP_CENTER);
        vbBtnMembers.setSpacing(10);
    }

    private void handleMemberButtonClick(Member member) {
        loadMemberPageScene(member);
    }

    private void loadMemberPageScene(Member member) {
        try {
            // Carrega o arquivo FXML da cena da página do membro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("memberPage.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da cena da página do membro
            MemberPageController memberPageController = loader.getController();

            // Configura as informações do membro no controlador da página do membro
            memberPageController.setMemberInfo(member);

            // Cria uma nova cena
            Scene memberPageScene = new Scene(root);

            // Obtém o palco atual
            Stage currentStage = (Stage) vbBtnMembers.getScene().getWindow();

            // Define a nova cena no palco atual
            currentStage.setScene(memberPageScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Lida com erros ao carregar a cena da página do membro
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

