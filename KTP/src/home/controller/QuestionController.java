package home.controller;

import home.model.KnowledgeBase;
import home.model.Question;
import home.model.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class QuestionController {

    private int nextID = 1;
    private KnowledgeBase kb;
    private SelectionBoxManager sbm;

    @FXML
    private Pane paneMain;

    @FXML
    private Button btnNext;

    @FXML
    private Label textFullQuestion;

    @FXML
    private VBox answerBox;

    public QuestionController() {
        this.kb = new KnowledgeBase();
        this.sbm = new SelectionBoxManager();

    }

    public void clickedNext(ActionEvent e) {
        Question question = kb.getQuestion(nextID);
        int idx = 0;
        for (Response response: question.getAnswers()) {
            sbm.getChecks().get(idx).setText(response.getValue());
        }
    }



}
