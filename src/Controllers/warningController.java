package Controllers;

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


    /**
     * Closes warning message stage
     */

    public void okay(){
        Stage stage = (Stage) warningLabel.getScene().getWindow();

        stage.close();
    }

    public void closeApp(){
        Platform.exit();
    }

    }