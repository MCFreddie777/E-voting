package Controllers;

import Models.User.*;
import Models.Other.*;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import java.util.regex.Pattern;

public class registerController {

    private @FXML JFXTextField usernameField;
    private @FXML JFXPasswordField passwordField;
    private @FXML JFXPasswordField confirmPasswordField;
    private @FXML JFXCheckBox accept;
    private @FXML JFXButton signUpButton;

    private UserDatabase database;
    private String username;
    private String password;
    private String confirmPassword;


    private static final Pattern [] passwordRegex = new Pattern[3];
    {
        passwordRegex[0] = Pattern.compile(".*[A-Z].*");
        passwordRegex[1] = Pattern.compile(".*[a-z].*");
        passwordRegex[2] = Pattern.compile(".*\\d.*");
    }

    private static final  Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-" + "zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$");

    public registerController(UserDatabase database){
        this.database = database;
    }

    private void loadFields(){
        username = usernameField.getText();
        password = passwordField.getText();
        confirmPassword = confirmPasswordField.getText();
    }

    public void closeApp() { System.exit(0); }

    public void goToLogin() {
        View.newView("/View/login.fxml",signUpButton,"E-vote - Log In","",false);
    }

    private void registerSuccessful() {
        View.newView("/View/registrationSuccessful.fxml", signUpButton, "E-vote - Registration Successful", "",false);
    }

    /**
     * In case of valid username & password, creates an account in database and saves it to file.
     */
    public void register() {
        if (verifyInput()) {
            database.addUser(username, password);
            database.saveToFile();
            registerSuccessful();
        }
    }

    /**
     * Checks email address if it is valid, using regular expressions
     * @param email String mail address
     * @return boolean value if it is valid email address
     */
    public boolean emailTypeChecker(String email) {

        if (email == null)
            return false;
        return emailRegex.matcher(email).matches();
    }

    /**
     * Checks password if it is valid, using regular expressions
     * @param pass String password
     * @return if password is valid according to regex
     */
    private boolean isLegalPassword(String pass) {

        for(int i = 0; i < passwordRegex.length; i++){
            if(!passwordRegex[i].matcher(pass).matches())
                return false;
        }
        return true;
    }

    public void showTermsOfUse() {
        Warning.showAlert(
                            "Terms of Use"
                            +"\n\nLast updated April 21,2018. Replaces the prior version in its entirety."
                            +"\n\nAny participation in this software will constitute acceptance of this agreement. If you do not agree to abide by the above, please do not use this software."
                            +"\n\nYou grant E-voting and all other persons or entities involved in the operation of the software the right to transmit, monitor, retrieve, store, and use your information in connection with the software. E-voting cannot and does not assume any responsibility or liability for any information you submit, including to your Account."
                            +"\n\nYou grant to E-voting the right to use all content you upload or otherwise transmit to this software, subject to these Terms and Conditions and E-voting’s Privacy Policy in any manner E-voting chooses, including, but not limited, to copying, displaying, performing or publishing it in any format whatsoever, modifying it, incorporating it into other material or making a derivative work based on it."
                            +"\n\nE-voting may reject, refuse to post or delete any content for any or no reason"
                            +"\n\nDevelopers may modify or amend terms without notice at any time and such modification will be effective upon posting by E-voting here."
        ,700);
    }

    /**
     * Verifies input if TextFields are not empty and checks e-mail TextField for correct pattern.
     * @return true if both of the TextFields are empty and e-mail is valid
     */
    private boolean verifyInput() {
        loadFields();
        String errMsg = "";
        if ((username.isEmpty()) &&  password.isEmpty() && confirmPassword.isEmpty()) {
           Warning.showAlert("Please enter all information");
           return false;
        }

        if (username.isEmpty()) {
            Warning.showAlert("Please enter your e-mail");
            return false;
        }

        if (!emailTypeChecker(username)) {
            Warning.showAlert("Invalid e-mail address");
            return false;
        }

        if (database.isInDatabase(username)) {
            Warning.showAlert("There is already account with that e-mail address.");
            return false;
        }

        if (password.isEmpty()) {
            Warning.showAlert("Please enter your password");
            return false;
        }

        if (!isLegalPassword(password) || (password.length() < 8)){
            Warning.showAlert("Password needs to be at least 8 characters long and contain at least one number, one lowercase and one uppercase letter.",650);
            return false;
        }

        if (confirmPassword.isEmpty()) {
            Warning.showAlert("Please confirm your password");
            return false;
        }

        if (!(password.equals(confirmPassword))) {
            Warning.showAlert("Passwords does not match");
            return false;
        }

        if (!accept.isSelected()) {
            Warning.showAlert("Please agree with our Terms of Use");
            return false;
        }

        return true;
    }

}
