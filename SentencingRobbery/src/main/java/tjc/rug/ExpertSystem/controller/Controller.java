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
    protected Model model;
    private BorderPane questionPane = null;
    private static Node prevPane = null;

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
        String path = "../../../../resources/fxml/" + page + ".fxml";
        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setCenter(root);
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
