package Controllers;

import Models.Other.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class registrationSuccessfulController {

    private @FXML Button backButton;


    public void closeApp() {
        System.exit(0);
    }

    public void goToLogin() {
        View.newView("/View/login.fxml",(Node)backButton,"E-vote - Log In","",false);
    }
}
