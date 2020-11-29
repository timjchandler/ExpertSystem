package home.controller;

import home.model.KnowledgeBase;
import home.model.Question;
import home.model.Response;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class QuestionController {

    private final Question question;
    private VBox vBox;

    public QuestionController(int QuestionID) {
        this.question = new KnowledgeBase().getQuestion(QuestionID);
        buildVBox();
    }

    private void buildVBox() {
        vBox = new VBox();
        vBox.setLayoutX(72.0);
        vBox.setLayoutY(54.0);
        vBox.setPrefHeight(331.0);
        vBox.setPrefWidth(267.0);

        if (question.getType().equals(Question.Type.SINGLE)) addRadioButtons();
        else if (question.getType().equals(Question.Type.MULTI)) addCheckBoxes();
    }

    private void addCheckBoxes() {
        for (Response response: question.getResponses()) {
            if (response.getValue().equals("")) break;
            CheckBox cb = new CheckBox(response.getValue());
            cb.setAlignment(Pos.CENTER_LEFT);
            cb.setPadding(new Insets(10, 0, 0, 0));
            vBox.getChildren().add(cb);
        }
    }

    private void addRadioButtons() {
        ToggleGroup toggleGroup = new ToggleGroup();
        for (Response response: question.getResponses()) {
            if (response.getValue().equals("")) break;
            RadioButton rb = new RadioButton(response.getValue());
            rb.setAlignment(Pos.CENTER_LEFT);
            rb.setPadding(new Insets(10, 0, 0, 0));
            rb.setToggleGroup(toggleGroup);
            vBox.getChildren().add(rb);
        }
    }

    public VBox getVBox() {
        return vBox;
    }

    public String getQuestionText() {
        return question.getQuestion();
    }

}
