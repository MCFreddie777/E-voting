package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class registrationSuccessfulController {

    @FXML Button backButton;

    private viewController viewCntrllr = new viewController();

    public void closeApp() {
        System.exit(0);
    }

    public void goToLogin() {
        //TODO done.
        viewCntrllr.newScreenWithButton("/View/login.fxml",backButton,"E-vote - Log In","");
    }
}
