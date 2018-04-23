package Controllers;

import Models.Other.View;
import Models.Other.Warning;
import Models.User.User;
import Models.User.UserDatabase;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;

public class votingPollController{

    private @FXML
    Label account;
    private @FXML
    Label dateLabel;
    private @FXML
    Label question;
    private @FXML
    JFXButton answer1;
    private @FXML
    JFXButton answer2;
    private @FXML
    Label questionCounter;
    private @FXML
    JFXButton nextQuestion;
    private @FXML
    BorderPane questionPane;
    private @FXML
    BorderPane graphPane;
    private @FXML
    PieChart graph;
    private @FXML
    Label graphQuestion;


    private Voting voting;
    private String date;
    private int pollCounter = 0;
    private int votingIndex;
    private User currentUsr;
    private LocalDate today;


    public votingPollController(Voting voting, String username, String date, int index, int thisMonth, LocalDate today){
        UserDatabase database =new UserDatabase("/src/Data/UsrData.csv");
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

    private void calculate(boolean choice){
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

    private void results(){
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

    private void end(){
        View.newView("/View/votingSuccessful.fxml",(Node)nextQuestion,"E-vote",new votingSuccessfulController(voting,currentUsr,votingIndex,today),false);
    }

}