package Controllers;

import Models.User.*;
import Models.Other.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;



public class loginController implements Initializable {

    private @FXML JFXTextField usernameField;
    private @FXML JFXPasswordField passwordField;
    private @FXML JFXButton logInButton;
    private @FXML JFXButton signUpButton;


    /**
     * Loads database during initalization
     */
    private UserDatabase database = new UserDatabase("/src/Data/UsrData.csv");


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        database.loadDatabase();
    }

    /**
     * Closes Window
     */
    public void closeApp() {
        System.exit(0);
    }

    /**
     * Verifies input if TextFields are not empty and checks e-mail TextField for correct pattern.
     * @return true if both of the TextFields are empty and e-mail TextField contains one ampersand symbol and at least one dot.
     */
    private boolean verifyInput() {
        registerController registerCntrllr = new registerController(database);
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ((username.equals("a"))&&(password.equals("a"))) return true; //JUST FOR TESTING PURPOSES USR: a, PASS: a //TODO DELETE AFTER

        if (username.isEmpty()) {
            Warning.showAlert("Please enter your e-mail.");
            return false;
        }

        if (password.isEmpty()) {
            Warning.showAlert("Please enter your password.");
            return false;
        }

        if(!registerCntrllr.emailTypeChecker(username)) {
            Warning.showAlert("Invalid email adress!");
            return false;
        }

        return true;
    }

    /**
     * After loading credentials from TextFields, verifies if the username and password exists in the user database.
     */
    public void logIn() {

        String username = usernameField.getText();
        String password = passwordField.getText();
        if (verifyInput()) {
            switch (database.isInDatabase(username,password)) {
                case 0: {
                    openVotingApp(username);
                    break;
                }
                case 1: {
                    Warning.showAlert("Invalid password!");
                    break;
                }
                case 2: {
                    Warning.showAlert("Username not found.");
                    break;
                }
            }
        }
   }

    /**
     * Switches scenes in stage after clicking on certain buttons.
     */
   public void goToRegister() {
       View.newView("/View/register.fxml",logInButton,"E-vote - Create an Account in E-Vote",new registerController(database),false);
   }

   private void openVotingApp(String username) {
       View.newView("/View/votingApp.fxml",logInButton,"E-vote",new votingAppController(username),true);

   }

}