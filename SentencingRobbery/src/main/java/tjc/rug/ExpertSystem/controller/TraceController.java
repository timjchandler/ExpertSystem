package tjc.rug.ExpertSystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tjc.rug.ExpertSystem.model.Response;
import tjc.rug.ExpertSystem.model.Sentence;

import java.net.URL;
import java.util.ResourceBundle;

public class TraceController extends Controller {

    private VBox qaBox;
    private Text factText;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label bannerLabel;

    @FXML
    private Button qButton;

    @FXML
    private Button fButton;

    @FXML
    private AnchorPane traceAP;

    public void initialize(URL location, ResourceBundle resources) {
        bannerLabel.setText(model.getSentence());
        factTrace();
        questionTrace();
        scrollPane.setContent(qaBox);
    }

    private void factTrace() {
        factText = new Text(model.getTrace());
        factText.getStyleClass().add("facttext");
    }

    public void tabSelect(ActionEvent e) {
        if (e.getSource().equals(qButton) && !scrollPane.getContent().equals(qaBox)) {
            scrollPane.setContent(qaBox);
            traceAP.setStyle("-fx-background-color: #353535");
        } else if (e.getSource().equals(fButton) && !scrollPane.getContent().equals(factText)) {
            scrollPane.setContent(factText);
            traceAP.setStyle("-fx-background-color: #1B1B1B");
        }
    }

    private void questionTrace() {
        qaBox = new VBox();
        qaBox.getStyleClass().add("vbox");
        for (Response response: Sentence.getResponses()) {
            Label q = new Label(response.getQText());
            Label a = new Label();
            q.getStyleClass().add("question");
            a.getStyleClass().add("answer");
            boolean multiAns = false;
            for (String ans: response.getAnswers()) {
                String prefix = multiAns ? "\n>>\t" : ">>\t";
                a.setText(a.getText() + prefix + ans);
                multiAns = true;
            }
            qaBox.getChildren().add(q);
            qaBox.getChildren().add(a);
        }
    }
}
