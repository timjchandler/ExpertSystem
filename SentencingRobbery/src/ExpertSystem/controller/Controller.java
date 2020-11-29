package ExpertSystem.controller;

import ExpertSystem.model.Fact;
import ExpertSystem.model.Model;
import ExpertSystem.model.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private enum CurrentView { QUESTIONS, TRACE, SETTINGS }

    private CurrentView currentView;
    private Model model;
    private ArrayList<RadioButton> rbArray = null;
    private ArrayList<CheckBox> cbArray = null;
    private BorderPane questionPane = null;


    @FXML
    Text traceText;

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

    public void mouseClicked(ActionEvent event) {
        if (event.getSource().equals(btnSide0)) restart();
        else if (event.getSource().equals(btnSide1)) trace();
        else if (event.getSource().equals(btnSide2)) System.out.println(btnSide2.getText());
        else if (event.getSource().equals(btnSide3)) System.out.println(btnSide3.getText());
    }

    private void restart() {
        currentView = CurrentView.QUESTIONS;
        btnSide0.setText("Restart");
        model.restart();
        loadPage("questionArea");
    }

    public void next() {
        if (rbArray != null) checkRB();
        if (cbArray != null) checkCB();
        rbArray = null;
        cbArray = null;
        buildScene(model.getQuestion(true));
    }

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
            temp.setStyle("-fx-background-color: #2A9D8F");
            temp.setMinSize(640, 600);
            temp.setPadding(new Insets(50, 50, 50, 50));
            ScrollPane sp = new ScrollPane();
            sp.setContent(temp);
            mainPane.setCenter(sp);
        }
    }

    private void loadPage(String page) {
        Parent root = null;

        String path = "../view/fxml/" + page + ".fxml";
        System.out.println(path);
        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(root);
    }

    private void checkCB() {
        if (model.getQuestion(false) == null) return;
        for (CheckBox cb: cbArray) {
            if (cb.isSelected()) {
                Fact fact = model.getQuestion(false).getAnswerFacts().get(cbArray.indexOf(cb));
                model.addFact(fact);
            }
        }
    }

    private void checkRB() {
        if (model.getQuestion(false) == null) return;
        for (RadioButton rb: rbArray) {
            if (rb.isSelected()) {
                Fact fact = model.getQuestion(false).getAnswerFacts().get(rbArray.indexOf(rb));
                model.addFact(fact);
            }
        }
    }

    private void buildScene(Question question) {
        if (question == null) {
            showSummary();
            return;
        }
        questionLabel.setText(question.getQuestionText());
        bannerLabel.setText(question.getHeading());
        topPane.setStyle("-fx-background-color: #FB8B24");
        next.setText("Next");
        questionVBox.getChildren().clear();
        if (question.getType() == Question.QuestionType.MULTI) buildMulti(question);
        else if (question.getType() == Question.QuestionType.SINGLE) buildSingle(question);
    }

    private void showSummary() {
        questionLabel.setText(model.getSentence());
        topPane.setStyle("-fx-background-color: #5F0F40");
        bannerLabel.setText("Recommended Sentence:");
        bannerLabel.setTextFill(Color.web("#9A031E"));
        questionVBox.getChildren().clear();
    }

    private void buildSingle(Question question) {
        ToggleGroup group = new ToggleGroup();
        rbArray = new ArrayList<>();
        for (String answer: question.getAnswers()) {
            RadioButton rb = new RadioButton(answer);
            rb.setToggleGroup(group);
            rb.setWrapText(true);
            rbArray.add(rb);
            rb.setPadding(new Insets(0, 0, 10, 0));
            questionVBox.getChildren().add(rb);
        }
    }

    private void buildMulti(Question question) {
        cbArray = new ArrayList<>();
        for (String answer: question.getAnswers()) {
            CheckBox cb = new CheckBox(answer);
            cbArray.add(cb);
            cb.setWrapText(true);
            cb.setPadding(new Insets(0, 0, 10, 0));
            questionVBox.getChildren().add(cb);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.model = new Model();
    }
}
