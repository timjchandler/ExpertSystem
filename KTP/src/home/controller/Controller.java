package home.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controller {

    private static int currentID = 1;

    @FXML
    private Button btnRestart;

    @FXML
    private Button btnTrace;

    @FXML
    private Button btnUndo;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnNext;

    @FXML
    private Pane boxBanner;

    @FXML
    private Label textBanner;

    @FXML
    private Label textFullQuestion;

    @FXML
    private Pane paneMain;

    @FXML
    private VBox answerBox;

    @FXML
    private VBox sideMenu;

    @FXML
    private void sortClicks(ActionEvent e) {
        if (e.getSource().equals(btnExit)) System.exit(0);
        else if (e.getSource().equals(btnSettings)) settings();
        else if (e.getSource().equals(btnRestart)) restart();
        else if (e.getSource().equals((btnTrace))) trace();
        else if (e.getSource().equals(btnUndo)) undo();
        else if (e.getSource().equals(btnNext)) next();
    }

    private void next() {
        QuestionController qc = new QuestionController(currentID);
        currentID = currentID % 4 + 1;
        answerBox.getChildren().clear();
        textFullQuestion.setText(qc.getQuestionText());
        answerBox.getChildren().add(qc.getVBox());
    }

    private void settings() {
        textBanner.setText("Settings was pressed. Not yet implemented.");
    }

    private void restart() {
        textBanner.setText("Restart was pressed. Not yet implemented.");
    }

    private void trace() {
        textBanner.setText("Trace was pressed. Not yet implemented.");
    }

    private void undo() {
        textBanner.setText("Undo was pressed. Not yet implemented.");
    }

}
