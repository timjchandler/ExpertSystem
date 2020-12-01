package tjc.rug.ExpertSystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import tjc.rug.ExpertSystem.model.Response;
import tjc.rug.ExpertSystem.model.Sentence;

import java.net.URL;
import java.util.ResourceBundle;

public class TraceController extends Controller {

    private VBox qaBox;
    private TextArea factArea;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label bannerLabel;

    public void initialize(URL location, ResourceBundle resources) {
        bannerLabel.setText(model.getSentence());
        factTrace();
        questionTrace();
        scrollPane.setContent(qaBox);
//        scrollPane.setContent(factArea);
    }

    private void factTrace() {
        factArea = new TextArea();
        factArea.setWrapText(true);
        factArea.setText(model.getTrace());
        factArea.getStyleClass().add("textarea");
    }

    // TODO: Set text box to minimum relative size
    private void questionTrace() {
        qaBox = new VBox();
        qaBox.getStyleClass().add("vbox");
        for (Response response: Sentence.getResponses()) {
            Label q = new Label(response.getQText());
            TextArea a = new TextArea();
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
