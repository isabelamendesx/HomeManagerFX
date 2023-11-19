package application.homemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private HBox hbWeeklyTasks;

    public void onBtnLogoutClick(ActionEvent e){
        Main.changeScreen("loginPage");
    }
    private Pane makeStyledPane(){
        Pane pane = new Pane();
        pane.getStyleClass().add("pane_1");
        return pane;
    }

    public void initializeWeeklyTasks(){
        Home home = Session.getInstance().getCurrentUser();

        for(Member member : home.getMembersList()){
            for(WeeklyTask wTask : member.getTarefasSemanais()){
                Pane pane = makeStyledPane(); // Método para criar a pane estilizada
                hbWeeklyTasks.getChildren().add(pane);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
