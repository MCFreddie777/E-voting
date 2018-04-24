package Controllers;

import Models.Other.TimeFlow;
import Models.Other.View;
import Models.Other.Warning;
import Models.User.User;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class addVotingController {

    @FXML
    private JFXTextField titleLabel;
    @FXML
    private JFXDatePicker dateFromCal;
    @FXML
    private JFXDatePicker dateToCal;
    @FXML
    private VBox pollBox;
    @FXML
    private JFXButton submitButton;
    @FXML
    private JFXButton cancelButton;


    private User currentUsr;
    private Voting currentVoting = new Voting();
    private TimeFlow timeFlow = new TimeFlow();
    private int clickedButton;   //NOT WORKING PART OF CODE


    public addVotingController(User currentUsr, LocalDate date){
        timeFlow.setDate(date);
        this.currentUsr = currentUsr;
    }

    public addVotingController(User currentUsr,Voting currentVoting, LocalDate date){
        timeFlow.setDate(date);
        this.currentUsr = currentUsr;
        this.currentVoting = currentVoting;
    }

    @FXML
    private void initialize(){

/*
        clickedButton = -1;
        for (int i=0;i<5;i++) {
            System.out.println(" Setting pollbox.children i: "+i + "poll (poll children clickedbutton: "+(clickedButton+1));
            pollBox.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    poll((JFXButton) pollBox.getChildren().get(++clickedButton));
                }
            });
        }
        //NOT WORKING PART OF CODE, MAYBE ILL DEAL WITH IT...
*/

/*
        clickedButton = -1;
        for (int i=0;i<5;i++){
            pollBox.getChildren().get(i).setOnMouseClicked(event -> poll((JFXButton)pollBox.getChildren().get(++clickedButton)));
        }
        WE NEED TO SHOW THIS TO MRS TEACHER
*/
        pollBox.getChildren().get(0).setOnMouseClicked(event -> poll((JFXButton) pollBox.getChildren().get(0),0));
        pollBox.getChildren().get(1).setOnMouseClicked(event -> poll((JFXButton) pollBox.getChildren().get(1),1));
        pollBox.getChildren().get(2).setOnMouseClicked(event -> poll((JFXButton) pollBox.getChildren().get(2),2));
        pollBox.getChildren().get(3).setOnMouseClicked(event -> poll((JFXButton) pollBox.getChildren().get(3),3));
        pollBox.getChildren().get(4).setOnMouseClicked(event -> poll((JFXButton) pollBox.getChildren().get(4),4));

        loadValuesFromVoting();
        refresh();
    }

    private void refresh(){
        for (int i=0;(i<5);i++){
            pollBox.getChildren().get(i).setVisible(false);
        }

        for (int i = 0;i<currentVoting.getPollCounter();i++){
            setPollButton(i);
        }
        if (currentVoting.getPollCounter()!=5) setAddButton(currentVoting.getPollCounter());
    }

    private void poll(JFXButton node,int index){
        addValuesToVoting();
        if (node.getText().equals("Add new poll")) {
            View.newView("/View/addPoll.fxml",pollBox,"E-vote - Add poll",new addPollController(currentUsr,currentVoting,timeFlow.getDate()),false);
        }
        else {
            View.newView("/View/addPoll.fxml",pollBox,"E-vote - Add poll",new addPollController(currentUsr,currentVoting,index,timeFlow.getDate()),false);
        }
    }

    private void addValuesToVoting(){
        currentVoting.setTitle(titleLabel.getText());
        if (!(dateFromCal.getValue()==null)) currentVoting.setDateFrom(dateFromCal.getValue());
        if (!(dateToCal.getValue()==null)) currentVoting.setDateTo(dateToCal.getValue());
    }

    private void loadValuesFromVoting(){
        if (!currentVoting.getTitle().isEmpty()) titleLabel.setText(currentVoting.getTitle());
        if (!(currentVoting.getDateFrom()==null)) dateFromCal.setValue(currentVoting.getDateFrom());
        if (!(currentVoting.getDateTo()==null)) dateToCal.setValue(currentVoting.getDateTo());
    }

    private  void setPollButton(int i){
        JFXButton pollButton = (JFXButton) pollBox.getChildren().get(i);
        pollButton.setVisible(true);
        pollButton.setPrefWidth(546);
        pollButton.setStyle("-fx-background-color: #D46A2E;-fx-text-fill: #ebebeb");
        pollButton.setText(currentVoting.getPolls().get(i).getQuestion());
    }

    private void setAddButton(int i){
        JFXButton addButton =  (JFXButton) pollBox.getChildren().get(i);
        addButton.setVisible(true);
        addButton.setPrefWidth(218);
        addButton.setStyle("-fx-background-color: #bbbbbb;-fx-text-fill: black");
        addButton.setText("Add new poll");
    }

    public void submitVoting(){
        addValuesToVoting();

        if (currentVoting.getTitle().isEmpty()) {
            Warning.showAlert("Please, enter the title of your voting.");
            return;
        }

        if ((currentVoting.getDateTo()==null) || (currentVoting.getDateFrom()==null)){
            Warning.showAlert("Oops! You forgot to set the availability!");
            return;
        }

        if (dateToCal.getValue().isBefore(dateFromCal.getValue())){
            Warning.showAlert("Unless you are a time-traveller, you can't do this. Availability ends before it's started.");
            return;
        }

        if (dateToCal.getValue().isBefore(timeFlow.getDate()) && dateFromCal.getValue().isBefore(timeFlow.getDate())){
            if (!Warning.showConfirmAlert("Warning! Your voting will be added, but it won't be available to vote, because it's expired. Are you sure you want to continue?"))
                return;
        }

        if (dateToCal.getValue().isEqual(dateFromCal.getValue())){
            if (!Warning.showConfirmAlert("Warning! Your voting will be open only for one day. Are you sure you want to continue?"))
                return;
        }

        if (currentVoting.getPollCounter()==0){
            Warning.showAlert("Please, add at least one poll.");
            return;
        }


        View.newView("/View/votingApp.fxml",titleLabel,"E-vote - Voting", new votingAppController(currentUsr, currentVoting,timeFlow.getDate()) ,false);
    }

    public void backToMainScreen() {
        View.newView("/View/votingApp.fxml", titleLabel, "E-vote - Voting", new votingAppController(currentUsr, timeFlow.getDate()), false);
    }

    public void closeApp(){
        if (Warning.showConfirmAlert("Do you really want to exit? You will be logged out automatically"))
            Platform.exit();
    }


}
