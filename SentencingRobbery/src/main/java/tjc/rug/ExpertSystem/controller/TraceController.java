package tjc.rug.ExpertSystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class TraceController extends Controller {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label bannerLabel;

    public void initialize(URL location, ResourceBundle resources) {
        bannerLabel.setText(model.getSentence());
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText(model.getTrace());
        textArea.getStyleClass().add("textarea");
        scrollPane.setContent(textArea);
    }
}
