package tjc.rug.ExpertSystem.controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane apHome;

    @FXML
    private ImageView bgImage;

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InputStream in = getClass().getResourceAsStream("/resources/media/home.png");
            bgImage.setImage(new Image(in));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRotate(circle1, true, 180, 8);
        setRotate(circle2, true, 120, 10);
        setRotate(circle3, true, 90, 12);

    }

    private void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }
}
