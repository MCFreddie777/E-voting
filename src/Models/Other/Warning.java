package Models.Other;

import Controllers.warningController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class Warning {

    /**
     * Shows an warning message in new stage using fxml
     */
    public static void showAlert(String message) {

        //View.newView("/View/warning.fxml",null,"Warning", new warningController(message),true);
        // this won't ever work, cuz we don't have a label from which we can get old scene and close it... we're not closing anything in case
        // of warning message mate... just poppin' out new one, thats it.

       try {
            FXMLLoader fxmlLoader = new FXMLLoader(Warning.class.getResource("/View/warning.fxml"));
            fxmlLoader.setController(new warningController(message));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Warning");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
     public static void showAlert(String message,int maxLabelSize) { //TODO there is an error
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Warning.class.getResource("/View/warning.fxml"));
            fxmlLoader.setController(new warningController(message,maxLabelSize));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Warning");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Opens a popup window with okay/cancel buttons which needs to be confirmed
     * @param message message which should be shown on popup window
     * @return true if user confirmed the message, otherwise it returns false
     */
    public static boolean showConfirmAlert(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Warning.class.getResource("/View/confirmWarning.fxml"));
            warningController popup = new warningController(message);
            fxmlLoader.setController(popup);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Please select your action.");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return (popup.getConfirmed());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

}
