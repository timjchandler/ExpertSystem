package home.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectionBoxManager implements Initializable {

    private ArrayList<CheckBox> check;
    private ArrayList<RadioButton> radio;

    @FXML
    private static CheckBox check1;

    @FXML
    private RadioButton radio1;

    @FXML
    private static CheckBox check2;

    @FXML
    private static CheckBox check3;

    @FXML
    private static CheckBox check4;

    @FXML
    private static CheckBox check5;

    @FXML
    private static CheckBox check6;

    @FXML
    private static CheckBox check7;

    @FXML
    private static CheckBox check8;

    @FXML
    private static CheckBox check9;

    @FXML
    private static CheckBox check10;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        check = new ArrayList<CheckBox>();
        radio = new ArrayList<RadioButton>();
        check.add(check1);
        check.add(check2);
        check.add(check3);
        check.add(check4);
        check.add(check5);
        check.add(check6);
        check.add(check7);
        check.add(check8);
        check.add(check9);
        check.add(check10);
        radio.add(radio1);
    }
    
    public ArrayList<CheckBox> getChecks() {
        return check;
    }
    
    public ArrayList<RadioButton> getRadio() {
        return radio;
    }
}
