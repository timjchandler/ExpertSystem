package tjc.rug.ExpertSystem.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static tjc.rug.ExpertSystem.controller.AnimateElement.setRotate;

public class ExitController implements Initializable {

    @FXML
    ImageView imageView;

    @FXML
    Circle circle0;

    @FXML
    Circle circle1;

    @FXML
    Circle circle2;

    public void back() {
        new Controller().exit();
    }

    public void exitFR() {
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InputStream in = getClass().getResourceAsStream("/resources/media/scales.png");
            imageView.setImage(new Image(in));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRotate(circle0, true, 120, 20);
        setRotate(circle1, true, 180, 15);
        setRotate(circle2, true,240, 12);
    }
}
