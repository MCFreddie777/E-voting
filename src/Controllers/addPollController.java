package Controllers;

import Models.Other.TimeFlow;
import Models.Other.View;
import Models.Other.Warning;
import Models.User.User;
import Models.Voting.Poll;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

public class addPollController {

    @FXML
    private Label votingTitleLabel;

    @FXML
    private JFXTextField questionLabel;

    @FXML
    private JFXTextField choice1Label;

    @FXML
    private JFXTextField choice2Label;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXButton cancelButton;

    private TimeFlow timeFlow = new TimeFlow();
    private User currentUsr;
    private Voting currentVoting;
    private int pollIndex = -1;


    public addPollController(User currentUsr, Voting currentVoting, LocalDate date){
        timeFlow.setDate(date);
        this.currentUsr = currentUsr;
        this.currentVoting = currentVoting;
    }

    public addPollController(User currentUsr, Voting currentVoting, int index, LocalDate date){
        timeFlow.setDate(date);
        this.currentUsr = currentUsr;
        this.currentVoting = currentVoting;
        this.pollIndex = index;
    }

    @FXML
    private void initialize(){
        votingTitleLabel.setText(currentVoting.getTitle().isEmpty() ? ("Add new poll") : (currentVoting.getTitle()+": Add new poll"));
        if (pollIndex!=-1){
            questionLabel.setText(currentVoting.getPolls().get(pollIndex).getQuestion());
            choice1Label.setText(currentVoting.getPolls().get(pollIndex).getChoices()[0]);
            choice2Label.setText(currentVoting.getPolls().get(pollIndex).getChoices()[1]);
        }
    }

    public void back(){
        View.newView("/View/addVoting.fxml", votingTitleLabel, "E-vote - Add Voting", new addVotingController(currentUsr, currentVoting,timeFlow.getDate()), false);
    }

    public void closeApp(){
        if (Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically"))
            Platform.exit();
    }

    public void submit(){
        if (questionLabel.getText().isEmpty()) {
            Warning.showAlert("Please enter your question.");
            return;
        }

        if ((choice1Label.getText().isEmpty()) || (choice2Label.getText().isEmpty())) {
            Warning.showAlert("You haven't filled one of the answer fields.");
            return;
        }

        Poll temp = new Poll("text",questionLabel.getText(),choice1Label.getText(),choice2Label.getText(),0,0);
        if (pollIndex==-1) currentVoting.addPoll(temp);
        else currentVoting.setPoll(temp,pollIndex);
        back();
    }


}


