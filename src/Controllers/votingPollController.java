package Controllers;

import Models.Other.Warning;
import Models.User.User;
import Models.User.UserDatabase;
import Models.Voting.PollDatabase;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class votingPollController{

    @FXML
    Label account;
    @FXML
    Label dateLabel;
    @FXML
    Label question;
    @FXML
    JFXButton answer1;
    @FXML
    JFXButton answer2;
    @FXML
    Label questionCounter;
    @FXML
    JFXButton nextQuestion;
    @FXML
    BorderPane questionPane;
    @FXML
    BorderPane graphPane;
    @FXML
    PieChart graph;
    @FXML
    Label graphQuestion;


    private Voting voting;
    private String date;
    private int pollCounter = 0;
    private int votingIndex;
    private User currentUsr;
    private UserDatabase database =new UserDatabase("/src/Data/UsrData.csv");
    private LocalDate today;




    public votingPollController(Voting voting, String username, String date, int index, int thisMonth, LocalDate today){
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
        setUi(pollCounter);
        Tooltip tooltip = new Tooltip("Click to show more information about your account. ");
        account.setTooltip(tooltip);
    }

    private void setUi(int poll){
        graphPane.setVisible(false);
        questionPane.setVisible(true);
        question.setText(voting.getPolls().get(poll).getQuestion());
        answer1.setText(voting.getPolls().get(poll).getChoices()[0]);
        answer2.setText(voting.getPolls().get(poll).getChoices()[1]);
        questionCounter.setText("Question "+(pollCounter+1)+"/"+voting.getPollCounter());
        nextQuestion.setVisible(false);
        answer1.setVisible(true);
        answer2.setVisible(true);
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

    public void nextQuestion(){
        setUi(++pollCounter);

    }

    public void answer1(){
        calculate(true);
        results();
    }

    public void answer2(){
        calculate(false);
        results();
    }

    public void calculate(boolean choice){
        int numOfVoters = voting.getVoterCount();
        Double a = 0.0;
        Double b = 0.0;
        double voterPart;
        if (numOfVoters!=0) {
            voterPart = 100 / numOfVoters;
            a = voting.getPolls().get(pollCounter).getStats()[0] / voterPart;
            b = voting.getPolls().get(pollCounter).getStats()[1] / voterPart;
        }
        if (choice) a++;
        else b++;
        voterPart = 100 / ++numOfVoters;
        voting.getPolls().get(pollCounter).setStats((a * voterPart), (b * voterPart));
    }

    public void results(){
        graphQuestion.setText(voting.getPolls().get(pollCounter).getQuestion());
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(voting.getPolls().get(pollCounter).getChoices()[0], voting.getPolls().get(pollCounter).getStats()[0]),
                        new PieChart.Data(voting.getPolls().get(pollCounter).getChoices()[1], voting.getPolls().get(pollCounter).getStats()[1]));
        graph.setData(pieChartData);
        graph.setClockwise(false);
        questionPane.setVisible(false);
        graphPane.setVisible(true);
        if ((pollCounter+1) == voting.getPollCounter()) {
            nextQuestion.setText("End");
            nextQuestion.setOnMouseClicked(event -> end());
        }
        nextQuestion.setVisible(true);
    }

    public void end(){
         //TODO done.
        viewController viewCntrllr = new viewController();
        votingSuccessfulController votingSuccessfullCntrllr =  new votingSuccessfulController(voting,currentUsr.getEmail(),date,votingIndex,currentUsr.getThisMonthVotings(),today);
        viewCntrllr.newScreenWithButton("/View/votingSuccessful.fxml",nextQuestion,"E-vote",votingSuccessfullCntrllr);

    }

}