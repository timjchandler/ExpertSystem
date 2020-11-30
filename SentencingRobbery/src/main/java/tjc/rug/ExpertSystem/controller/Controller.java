package tjc.rug.ExpertSystem.controller;

import tjc.rug.ExpertSystem.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    protected enum CurrentView { QUESTIONS, TRACE, HOME, SETTINGS }

    protected static Model model;
    protected static Node prevPane = null;
    private CurrentView currentView;
    private BorderPane questionPane = null;


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

    /**
     * Loads the model
     * @param location      Not used
     * @param resources     Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
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
        else if (event.getSource().equals(btnExit)) exit();
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
            btnSide1.setText("Trace");
            currentView = CurrentView.QUESTIONS;
            mainPane.setCenter(prevPane);
        }
        else {
            currentView = CurrentView.TRACE;
            prevPane = mainPane.getCenter();
            btnSide1.setText("Hide Trace");
            loadPage("trace");
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
     * Switches the main view to the exit screen
     */
    private void exit() {
        prevPane = mainPane.getCenter();
        loadPage("exitScreen");
    }
}
