package tjc.rug.ExpertSystem.controller;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitControllerOld implements Initializable {

    @FXML
    AnchorPane questionAP;

    @FXML
    Button exitNo;

    @FXML
    Pane pane1;

    @FXML
    Pane pane2;

    @FXML
    Pane pane3;

    /**
     * Shuts down the gui and terminates the program
     */
    public void exitFR() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Returns to the previous screen
     */
    public void back() {
        new Controller().exit();
    }

    private void animateBlock(Pane p, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), p);
        rt.setByAngle(angle);
        rt.setAutoReverse(reverse);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animateBlock(pane1, false,180, 30);
        animateBlock(pane2, true,180, 40);
        animateBlock(pane3, false,180, 20);
    }
}
