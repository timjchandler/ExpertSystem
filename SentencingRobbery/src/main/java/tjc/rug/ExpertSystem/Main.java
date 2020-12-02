package tjc.rug.ExpertSystem;

import javafx.scene.image.Image;
import tjc.rug.ExpertSystem.controller.Controller;
import tjc.rug.ExpertSystem.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model();
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/main.fxml"));
        primaryStage.setTitle(model.getTitle());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/media/icon3.png")));

        new Controller(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            URL iconURL = Main.class.getResource("/resources/media/icon3.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            // Sets task bar icon and name on mac, not needed for Windows or Linux
        }
        launch(args);
    }
}
