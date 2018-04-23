package Controllers;

import Models.Other.TimeFlow;
import Models.Other.View;
import Models.Other.Warning;
import Models.User.User;
import Models.Voting.Poll;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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


    public addPollController(User currentUsr, Voting currentVoting, LocalDate date){
        timeFlow.setDate(date);
        this.currentUsr = currentUsr;
        this.currentVoting = currentVoting;
    }

    @FXML
    private void initialize(){
        votingTitleLabel.setText(currentVoting.getTitle().isEmpty() ? ("Add new poll") : (currentVoting.getTitle()+": Add new poll"));
    }

    public void back(){
        View.newView("/View/addVoting.fxml", votingTitleLabel, "E-vote - Add Voting", new addVotingController(currentUsr, currentVoting,timeFlow.getDate()), false);
    }



    public void closeApp(){
        Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically");
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

        currentVoting.addPoll(new Poll("text",questionLabel.getText(),choice1Label.getText(),choice2Label.getText(),0,0));
        back();
    }


}


