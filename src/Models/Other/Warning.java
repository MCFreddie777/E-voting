package Models.Other;

import Controllers.warningController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class Warning {

    /**
     * Shows an warning message in new stage using fxml
     */
    public static void showAlert(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Warning.class.getResource("/View/warning.fxml"));
            fxmlLoader.setController(new warningController(message));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Warning");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
     public static void showAlert(String message,int maxLabelSize) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Warning.class.getResource("/View/warning.fxml"));
            fxmlLoader.setController(new warningController(message,maxLabelSize));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Warning");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void showConfirmAlert(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Warning.class.getResource("/View/confirmWarning.fxml"));
            fxmlLoader.setController(new warningController(message));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Please select your action.");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
