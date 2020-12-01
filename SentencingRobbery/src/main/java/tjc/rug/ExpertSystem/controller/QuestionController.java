package tjc.rug.ExpertSystem.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tjc.rug.ExpertSystem.model.Fact;
import tjc.rug.ExpertSystem.model.Question;
import tjc.rug.ExpertSystem.model.Sentence;

import java.util.ArrayList;

public class QuestionController extends Controller {

    private ArrayList<RadioButton> rbArray = null;
    private ArrayList<CheckBox> cbArray = null;

    @FXML
    Label labelBelowNext;

    @FXML
    VBox questionVBox;

    @FXML
    Label questionLabel;

    @FXML
    Button next;

    @FXML
    Pane topPane;

    @FXML
    Label bannerLabel;


    /**
     * React to the button "next" being pressed. If no options were selected, present a prompt
     * to select something. Pass over to methods that record new facts and set the button arrays
     * to null
     */
    public void next() {
        boolean selectionMade = false;
        if (rbArray != null) selectionMade = checkRB();
        if (cbArray != null) selectionMade = checkCB();
        if (!selectionMade && (rbArray != null || cbArray != null)) {
            labelBelowNext.setText("Please select an answer");
            return;
        }
        labelBelowNext.setText("");
        rbArray = null;
        cbArray = null;
        buildScene(model.getQuestion(true));
    }

    /**
     * Build the main question panel, update the question text and pass to other methods to add buttons.
     * If no new questions available shows a summary of gathered information
     * @param question      The question to build around
     */
    private void buildScene(Question question) {
        if (question == null) {
            showSummary();
            return;
        }
        questionLabel.setText(question.getQuestionText());
        bannerLabel.setText(question.getHeading());
        next.setText("Next");
        questionVBox.getChildren().clear();
        buildButtons(question);
    }

    /**
     * Changes the visuals of the question pane to indicate the end, shows the calculated recommended
     * sentence
     */
    private void showSummary() {
        System.out.println(model.getSentence());
        questionLabel.setText(model.getSentence());
        topPane.setStyle("-fx-background-color: #1E1E24");
        bannerLabel.setText("Recommended Sentence:");
        bannerLabel.setTextFill(Color.web("#9A031E"));
        questionVBox.getChildren().clear();
    }

    /**
     * Check the CheckBox ArrayList for new facts, add them to the model.
     * @return      True if facts obtained, false otherwise
     */
    private boolean checkCB() {
        if (model.getQuestion(false) == null) return false;
        boolean out = false;
        for (CheckBox cb: cbArray) {
            if (cb.isSelected()) {
                Fact fact = model.getQuestion(false).getAnswerFacts().get(cbArray.indexOf(cb));
                new Sentence(model.getQuestion(false), cbArray.indexOf(cb));
                model.addFact(fact);
                out = true;
            }
        }
        return out;
    }

    /**
     * Check the RadioButton ArrayList for new facts, add them to the model.
     * @return      True if facts obtained, false otherwise
     */
    private boolean checkRB() {
        if (model.getQuestion(false) == null) return false;
        boolean out = false;
        for (RadioButton rb: rbArray) {
            if (rb.isSelected()) {
                Fact fact = model.getQuestion(false).getAnswerFacts().get(rbArray.indexOf(rb));
                new Sentence(model.getQuestion(false), rbArray.indexOf(rb));
                model.addFact(fact);
                out = true;
            }
        }
        return out;
    }

    /**
     * Generates the buttons to display: RadioButtons for single choice answers or CheckBoxes for multi choice
     * Stores the buttons in the relevant ArrayList and adds them to the VBox
     * @param question  The question from which to add the answers
     */
    private void buildButtons(Question question) {
        boolean multi = question.getType() == Question.QuestionType.MULTI;
        if (multi) cbArray = new ArrayList<>();
        else rbArray = new ArrayList<>();
        ToggleGroup group = new ToggleGroup();
        for (String answer: question.getAnswers()) {
            ButtonBase btn = multi ? new CheckBox(answer) : new RadioButton(answer);
            btn.getStyleClass().add("optionbutton");
            btn.setWrapText(true);
            btn.setPadding(new Insets(0, 0, 10, 0));
            questionVBox.getChildren().add(btn);
            if (multi) cbArray.add((CheckBox) btn);
            else {
                ((RadioButton) btn).setToggleGroup(group);
                rbArray.add((RadioButton) btn);
            }
        }
    }
}
