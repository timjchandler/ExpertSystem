package tjc.rug.ExpertSystem.controller;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tjc.rug.ExpertSystem.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Stage primaryStage = null;

    protected enum CurrentView { LOAD, QUESTIONS, TRACE, HOME, SETTINGS, EXIT }

    protected static Model model;
    protected static Node prevPane = null;
    protected static BorderPane homePane = null;
    private static CurrentView currentView = CurrentView.LOAD;
    private double xOffset;
    private double yOffset;

    @FXML
    BorderPane mainPane;

    @FXML
    Pane topLeftPane;

    @FXML
    Circle minCircle;

    @FXML
    Circle closeCircle;

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

    public Controller() {
    }

    public Controller(Stage primaryStage) {
        Controller.primaryStage = primaryStage;
    }

    /**
     * Loads the model
     * @param location      Not used
     * @param resources     Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        if (currentView == CurrentView.LOAD) {
            loadPage("home");
            currentView = CurrentView.HOME;
        }
        initDraggable();
    }

    /**
     * Allows the gui to be moved around the screen by clicking and dragging on the top left corner
     */
    private void initDraggable() {
        topLeftPane.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        topLeftPane.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

    /**
     * Responds to a mouse click by passing over to a relevant method
     * @param event     The event representing the mouse click
     */
    public void mouseClicked(ActionEvent event) {
        if (event.getSource().equals(btnSide0)) restart();
        else if (event.getSource().equals(btnSide1)) trace();
        else if (event.getSource().equals(btnSide2)) help();
        else if (event.getSource().equals(btnSide3)) temporary();
        else if (event.getSource().equals(btnExit)) exit();
    }

    /**
     * Opens the readme in github pages through the preferred browser
     */
    private void help() {
        try {
            URI u = new URI("https://timjchandler.github.io/ExpertSystem/");
            java.awt.Desktop.getDesktop().browse(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void temporary() {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println(timestamp.split("[.]")[0]);
    }

    /**
     * Restart the system, reloading the model and moving to the home page. If on the home page already
     * move to questions
     */
    private void restart() {
        if (currentView == CurrentView.HOME) {
            btnSide0.setText("Restart");
            currentView = CurrentView.QUESTIONS;
            loadPage("questionArea");
        }
        else {
            currentView = CurrentView.HOME;
            btnSide0.setText("Start");
            btnSide1.setText("Trace");
            model.restart();
            loadPage("home");
        }
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
        String path = "/resources/fxml/" + page + ".fxml";
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
    public void exit() {
        if (currentView == CurrentView.EXIT) {
            currentView = CurrentView.QUESTIONS;
            homePane.setCenter(prevPane);
        }
        else {
            currentView = CurrentView.EXIT;
            prevPane = mainPane.getCenter();
            homePane = mainPane;
            loadPage("exit");
        }
    }

    /**
     * Minimises the gui to the tak/start bar
     */
    public void minimise() {
        primaryStage.setIconified(true);
    }
}
