package tjc.rug.ExpertSystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tjc.rug.ExpertSystem.model.Model;
import tjc.rug.ExpertSystem.model.Response;
import tjc.rug.ExpertSystem.model.Sentence;

import java.net.URL;
import java.util.ResourceBundle;

public class TraceController implements Initializable {

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

    /**
     * Sets the recommended sentence as a banner at the top, generates the trace objects, shows the question
     * trace
     * @param location      Not used
     * @param resources     Not used
     */
    public void initialize(URL location, ResourceBundle resources) {
        bannerLabel.setText(Model.getSentence());
        factTrace();
        questionTrace();
        scrollPane.setContent(qaBox);
    }

    /**
     * Loads the trace of facts as a string to the factText Text object, sets the style css
     */
    private void factTrace() {
        factText = new Text();
        factText.getStyleClass().add("facttext");
        if (Model.isNull() || Sentence.isNull()) return;
        factText.setText(Model.getTrace());
    }

    /**
     * Switches between the facts and the questions trace. Changes background colour to match the selected tab
     * @param e     The triggering ActionEvent
     */
    public void tabSelect(ActionEvent e) {
        if (e.getSource().equals(qButton) && !scrollPane.getContent().equals(qaBox)) {
            scrollPane.setContent(qaBox);
            traceAP.setStyle("-fx-background-color: linear-gradient(to top left, #565656, #100A0E)");
            setButton(fButton, qButton);
        } else if (e.getSource().equals(fButton) && !scrollPane.getContent().equals(factText)) {
            scrollPane.setContent(factText);
            traceAP.setStyle("-fx-background-color: linear-gradient(to top left, #100A0E, #565656)");
            setButton(qButton, fButton);
        }
    }

    private void setButton(Button on, Button off) {
        on.setStyle("-fx-background-color: rgba(27, 27, 27, 1); -fx-border-width: 2;");
        off.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
    }

    /**
     * Generates the question trace. Creates a label for each question, sets its style and adds it to the qaBox object
     * Then adds the responses as a Text object below, also in the qaBox.
     */
    private void questionTrace() {
        qaBox = new VBox();
        qaBox.getStyleClass().add("vbox");
        if (Model.isNull() || Sentence.isNull()) return;
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
