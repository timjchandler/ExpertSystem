package tjc.rug.ExpertSystem.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ExitController extends Controller {

    @FXML
    AnchorPane questionAP;

    @FXML
    Button exitNo;

    /**
     * Extends the supers method for clicks on the "No" button on the exit screen
     * @param event     The event representing the mouse click
     */
    public void mouseClicked(ActionEvent event) {
        super.mouseClicked(event);
        if (event.getSource().equals(exitNo)) back();
    }

    /**
     * Shuts down the gui and terminates the program
     */
    public void exitFR() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Returns to the previous screen
     */
    private void back() {
        ((BorderPane) questionAP.getParent()).setCenter(prevPane);
    }

}
