package tjc.rug.ExpertSystem;

import tjc.rug.ExpertSystem.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model();
//        // TODO: TEST THIS METHOD INSTEAD
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/main.fxml"));
//        loader.setController(new Controller());
//        Parent root = loader.load();
//        // TODO: END

        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/main.fxml"));
        primaryStage.setTitle(model.getTitle());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
