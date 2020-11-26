package home.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

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

    private void manageClicks(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnExit)) System.exit(0);
    }
}
