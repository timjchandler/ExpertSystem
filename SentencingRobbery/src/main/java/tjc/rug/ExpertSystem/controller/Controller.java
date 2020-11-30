package tjc.rug.ExpertSystem.controller;

import tjc.rug.ExpertSystem.model.Fact;
import tjc.rug.ExpertSystem.model.Model;
import tjc.rug.ExpertSystem.model.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private enum CurrentView { QUESTIONS, TRACE, HOME, SETTINGS }

    private CurrentView currentView;
    private Model model;
    private ArrayList<RadioButton> rbArray = null;
    private ArrayList<CheckBox> cbArray = null;
    private BorderPane questionPane = null;
    private static Node prevPane = null;

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

    @FXML
    BorderPane mainPane;

    @FXML
    Button btnSide0;

    @FXML
    Button btnSide1;

    @FXML
    Button btnSide2;

    @FXML
    Button btnSide3;

    @FXML
    Button btnExit;

    @FXML
    Button exitNo;

    @FXML
    AnchorPane questionAP;

    /**
     * Loads the model
     * @param location      Not used
     * @param resources     Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = new Model();
    }

    /**
     * Responds to a mouse click by passing over to a relevant method
     * @param event     The event representing the mouse click
     */
    public void mouseClicked(ActionEvent event) {
        if (event.getSource().equals(btnSide0)) restart();
        else if (event.getSource().equals(btnSide1)) trace();
        else if (event.getSource().equals(btnSide2)) System.out.println(btnSide2.getText());
        else if (event.getSource().equals(btnSide3)) System.out.println(btnSide3.getText());
        else if (event.getSource().equals(btnExit)) exit(true);
        else if (event.getSource().equals(exitNo)) exit(false);
    }

    /**
     * Restart the system, reloading the model and resetting the view
     */
    private void restart() {
        currentView = CurrentView.QUESTIONS;
        btnSide0.setText("Restart");
        model.restart();
        loadPage("questionArea");
    }

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
     * Generate and show the trace of gathered facts and implications. If the trace
     * is already shown, hide it
     */
    private void trace() {
        if (currentView == CurrentView.TRACE) {
            if (questionPane != null) {
                currentView = CurrentView.QUESTIONS;
                mainPane.setCenter(questionPane);
            }
        }
        else {
            questionPane = (BorderPane) mainPane.getCenter();
            currentView = CurrentView.TRACE;
            Label temp = new Label(model.getTrace());
            temp.setStyle("-fx-background-color: #6B818C");
            temp.setMinSize(640, 600);
            temp.setPadding(new Insets(50, 50, 50, 50));
            ScrollPane sp = new ScrollPane();
            sp.setContent(temp);
            mainPane.setCenter(sp);
        }
    }

    /**
     * Load a page from a .fxml file and set it into the main pane
     * @param page      The name of the page to be loaded
     */
    private void loadPage(String page) {
        Parent root = null;
        String path = "../../../../viewresources/fxml/" + page + ".fxml";
        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(root);
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
                model.addFact(fact);
                out = true;
            }
        }
        return out;
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
        if (question.getType() == Question.QuestionType.MULTI) buildMulti(question);
        else if (question.getType() == Question.QuestionType.SINGLE) buildSingle(question);
    }

    /**
     * Changes the visuals of the question pane to indicate the end, shows the calculated recommended
     * sentence
     */
    private void showSummary() {
        questionLabel.setText(model.getSentence());
        topPane.setStyle("-fx-background-color: #1E1E24");
        bannerLabel.setText("Recommended Sentence:");
        bannerLabel.setTextFill(Color.web("#9A031E"));
        questionVBox.getChildren().clear();
    }

    /**
     * Adds the buttons for a single choice question
     * @param question  The question to add
     */
    private void buildSingle(Question question) {
        ToggleGroup group = new ToggleGroup();
        rbArray = new ArrayList<>();
        for (String answer: question.getAnswers()) {
            RadioButton rb = new RadioButton(answer);
            rb.setToggleGroup(group);
            rb.getStyleClass().add("radiobutton");
            rb.setWrapText(true);
            rbArray.add(rb);
            rb.setPadding(new Insets(0, 0, 10, 0));
            questionVBox.getChildren().add(rb);
        }
    }

    /**
     * Adds the buttons for a multi choice question
     * @param question  The question to add
     */
    private void buildMulti(Question question) {
        cbArray = new ArrayList<>();
        for (String answer: question.getAnswers()) {
            CheckBox cb = new CheckBox(answer);
            cbArray.add(cb);
            cb.getStyleClass().add("checkbox");
            cb.setWrapText(true);
            cb.setPadding(new Insets(0, 0, 10, 0));
            questionVBox.getChildren().add(cb);
        }
    }

    /**
     * Shuts down the gui and terminates the program
     */
    public void exitFR() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Changes the exit button text to "back" (or resets it to "exit") switches screen to and from
     * the exit screen
     */
    private void exit(Boolean toPage) {
        if (toPage) {
            prevPane = mainPane.getCenter();
            loadPage("exitScreen");

        }
        else ((BorderPane) questionAP.getParent()).setCenter(prevPane);
    }
}
