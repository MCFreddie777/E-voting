package Controllers;

import Models.Other.View;
import Models.Other.Warning;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXTextField;


import java.io.IOException;

public class warningController {

    private @FXML Label warningLabel;
    private String warningMessage;
    private int maxLabelSize = 0;
    private Stage from;
    private boolean confirmed;
    /**
     * Sets warning message to Label in fxml
     */
    @FXML
    private void initialize() {
        warningLabel.setText(warningMessage);
        if (maxLabelSize != 0) {
            warningLabel.setMaxWidth(maxLabelSize);
        }
    }

    public warningController(String message) {
        warningMessage = message;
    }

    public warningController(String message,int maxLabelSize) {
        this.maxLabelSize = maxLabelSize;
        warningMessage = message;
    }

    public warningController(String message,Stage from) {
        warningMessage = message;
        this.from = from;
    }

    /**
     * Closes warning message stage
     */

    public void confirm(){
        confirmed = true;
        exit();
    }
    public void cancel(){
        confirmed = false;
        exit();
    }
    private void exit(){
        Stage stage = (Stage) warningLabel.getScene().getWindow();
        stage.close();
    }

    public boolean getConfirmed(){
        return confirmed;
    }

}