package Controllers;

import Models.User.*;
import Models.Other.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;



public class loginController {

    private @FXML JFXTextField usernameField;
    private @FXML JFXPasswordField passwordField;
    private @FXML JFXButton logInButton;
    private @FXML JFXButton signUpButton;


    /**
     * Loads database during initalization
     */
    private UserDatabase database = new UserDatabase("/data/UsrData.csv");


    @FXML
    public void initialize(){
        database.loadDatabase();
    }

    public void closeApp() {
        System.exit(0);
    }

    /**
     * Checks if username & password fields aren't empty and if e-mail is correct
     * @return true if e-mail and password is correct and e-mail is valid
     */
    private boolean verifyInput() {
        registerController registerCntrllr = new registerController(database);
        String username = usernameField.getText();
        String password = passwordField.getText();

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
     *  verifies if the username exists in database and if yes, checks if password is correct .
     */
    public void logIn() {

        String username = usernameField.getText();
        String password = passwordField.getText();
        if (verifyInput()) {
            switch (database.isInDatabase(username,password)) {
                case 0: {
                    User temp = database.getUserByUsername(username);
                    temp.setEmail(username);
                    openVotingApp(temp);
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

   public void goToRegister() {
       View.newView("/View/register.fxml",logInButton,"E-vote - Create an Account in E-Vote",new registerController(database),false);
   }

   private void openVotingApp(User user) {
       View.newView("/View/votingApp.fxml",logInButton,"E-vote",new votingAppController(user),true);
   }

}