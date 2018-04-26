package Controllers;

import Models.Other.View;
import Models.Other.Warning;
import Models.User.User;
import Models.User.UserDatabase;
import Models.Voting.PollDatabase;
import Models.Voting.Voting;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class votingSuccessfulController {

    private @FXML
    Label account;
    private @FXML
    Label dateLabel;
    private @FXML
    Label votingTitle;
    private @FXML
    Label voterCount;

    private Voting voting;
    private int votingIndex;
    private User currentUsr;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private UserDatabase database =new UserDatabase("/data/UsrData.csv");
    private PollDatabase votings = new PollDatabase("/data/PollData.csv");

    private LocalDate today;


    public votingSuccessfulController(Voting voting, User currentUsr, int index, LocalDate today){
        this.voting = voting;
        this.votingIndex = index;
        this.currentUsr = currentUsr;
        this.today = today;
    }

    @FXML
    private void initialize(){
        account.setText(currentUsr.getEmail());
        dateLabel.setText("Today: "+today.format(format));
        votingTitle.setText("You have successfully completed "+voting.getTitle()+" voting!");
        voterCount.setText("You and "+voting.getVoterCount()+" other voters already voted.");

        currentUsr.addCompletedVoting();
        database.loadDatabase();
        database.updateUser(currentUsr);
        database.saveToFile();


        votings.loadDatabase();
        voting.addVoter(currentUsr.getEmailHash());
        votings.getVoting(votingIndex).replaceStats(voting);
        votings.saveToFile();
        votings.loadDatabase();



        Tooltip tooltip = new Tooltip("Click to show more information about your account. ");
        account.setTooltip(tooltip);
    }

    public void backToMainScreen(){
        View.newView("/View/votingApp.fxml",account,"E-vote - Voting", new votingAppController(currentUsr,today),false);
    }

    public void showAccountStatistics(){
        String message = "Username/e-mail: "+currentUsr.getEmail()+"\n\n";
        message += "If you wish to change your password, please contact us by sending e-mail to frantisek.gic@gmail.com\n\n";
        message += "Votings completed (this month): "+currentUsr.getThisMonthVotings()+"\n";
        message += "Votings completed (total): "+currentUsr.getCompletedVotings()+"\n";
        message += "Created own votings (this month): "+currentUsr.getThisMonthCreated()+"\n";
        message += "Created own votings (total): "+currentUsr.getTotalCreated()+"\n";
        Warning.showAlert(message,500);
    }

    public void closeApp(){
        if (Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically"))
            Platform.exit();
    }

    public void logOut(){
        if (Warning.showConfirmAlert("Do you really want to log out?")) {
            View.newView("/View/login.fxml",account,"E-vote - Log In","",true);
            Warning.showAlert("You have been successfully logged out");
        }
    }
}