package Controllers;

import Models.Other.Warning;
import Models.User.User;
import Models.User.UserDatabase;
import Models.Voting.Voting;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class votingSuccessfulController {

    @FXML
    Label account;
    @FXML
    Label dateLabel;
    @FXML
    Label votingTitle;
    @FXML
    Label voterCount;

    private Voting voting;
    private String date;
    private int votingIndex;
    private User currentUsr;
    private UserDatabase database =new UserDatabase("/src/Data/UsrData.csv");
    private LocalDate today;


    private viewController viewCntrllr = new viewController();


    public votingSuccessfulController(Voting voting, String username, String date, int index, int thisMonth, LocalDate today){
        this.voting = voting;
        this.date = date;
        this.votingIndex = index;
        database.loadDatabase();
        this.currentUsr = database.getUserByUserName(username);
        currentUsr.setThisMonthVotings(thisMonth);
        this.today = today;

    }

    @FXML
    private void initialize(){
        account.setText(currentUsr.getEmail());
        dateLabel.setText("Today: "+date);
        votingTitle.setText("You have successfully completed "+voting.getTitle()+" voting!");
        voterCount.setText("You and "+voting.getVoterCount()+" other voters already voted.");
        voting.addVoter(currentUsr.getEmail());
        currentUsr.addCompletedVoting();
        database.saveToFile();
        Tooltip tooltip = new Tooltip("Click to show more information about your account. ");
        account.setTooltip(tooltip);
    }

    public void backToMainScreen(){
        //TODO done.
         votingAppController votingAppCntrllr = new votingAppController(currentUsr.getEmail(),voting,votingIndex,currentUsr.getThisMonthVotings(),today);
        viewCntrllr.newScreenWithLabel("/View/votingApp.fxml",account,"E-vote - Voting", votingAppCntrllr);
    }

    public void showAccountStatistics(){
        String message = "Username/e-mail: "+currentUsr.getEmail()+"\n\n";
        message += "If you forgot your password, please contact us by sending e-mail to frantisek.gic@gmail.com\n\n";
        message += "Votings completed (this month): "+currentUsr.getThisMonthVotings()+"\n";
        message += "Votings completed (total): "+currentUsr.getCompletedVotings()+"\n";
        message += "Created own votings (this month): "+currentUsr.getThisMonthCreated()+"\n";
        message += "Created own votings (total): "+currentUsr.getTotalCreated()+"\n";
        Warning.showAlert(message,500);
    }

    public void closeApp(){
        Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically");
    }

    public void logOut(){
        //TODO done.
        viewCntrllr.newScreenWithLabel("/View/login.fxml",account,"E-vote - Log In","");
    }
}