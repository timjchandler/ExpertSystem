package tjc.rug.ExpertSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static tjc.rug.ExpertSystem.controller.AnimateElement.setRotate;

public class HomeController implements Initializable {

    @FXML
    private ImageView bgImage;

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    /**
     * Loads the home image, sets the animation in motion
     * @param location  Unused
     * @param resources Unused
     */
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
}
