package Controllers;

import Models.Other.NetFile;
import Models.Other.TimeFlow;
import Models.Other.View;
import Models.Other.Warning;
import Models.User.User;
import Models.User.UserDatabase;
import Models.Voting.PollDatabase;
import Models.Voting.Voting;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class votingAppController {

    private @FXML Label account;
    private @FXML Label dateLabel;
    private @FXML AnchorPane anchorParent;
    private @FXML JFXButton previousPageButton;
    private @FXML JFXButton nextPageButton;


    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private TimeFlow timeFlow = new TimeFlow();
    private int totalPages;
    private int page= -1;
    private PollDatabase votings = new PollDatabase("/src/Data/PollData.csv");
    private UserDatabase database =new UserDatabase("/src/Data/UsrData.csv");
    private User currentUsr;



    public votingAppController(User currentUsr){
        database.loadDatabase();
        votings.loadDatabase();
        this.currentUsr = currentUsr;

    }

    public votingAppController(User currentUsr,Voting voting, int index,LocalDate date){
        votings.loadDatabase();
        votings.getVoting(index).replaceStats(voting);
        votings.saveToFile();

        timeFlow.setDate(date);

        database.loadDatabase();
        this.currentUsr = currentUsr;
        database.updateUser(currentUsr);
        database.saveToFile();
    }

    public votingAppController(User currentUsr,LocalDate date){
        votings.loadDatabase();
        timeFlow.setDate(date);
        this.currentUsr = currentUsr;
    }

    public votingAppController(User currentUsr,Voting voting,LocalDate date){
        votings.loadDatabase();
        votings.addVoting(voting);
        votings.saveToFile();

        timeFlow.setDate(date);

        database.loadDatabase();
        this.currentUsr = currentUsr;
        database.updateUser(currentUsr);
        database.saveToFile();
    }

    @FXML
    private void initialize(){

        anchorParent.getChildren().get(0).setOnMouseClicked(event -> openPoll(0));
        anchorParent.getChildren().get(1).setOnMouseClicked(event -> openPoll(1));
        anchorParent.getChildren().get(2).setOnMouseClicked(event -> openPoll(2));
        anchorParent.getChildren().get(3).setOnMouseClicked(event -> openPoll(3));

        account.setText(currentUsr.getEmail());

        dateLabel.setText("Today: "+timeFlow.toString());

        totalPages = ((votings.size()-1) / 4)+1;
        nextPage();

        Tooltip tooltip = new Tooltip("Click to show more information about your account. ");
        account.setTooltip(tooltip);

    }

    /**
     * Switches to next page
     */
    public void nextPage(){
        setInvisible();
        page++;
        for (int i=0;(i<4);i++){
            if (((page*4)+i) < votings.size()) {
                    Label labelTitle = (Label) ((VBox) ((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(0);
                    anchorParent.getChildren().get(i).setVisible(true);
                    addToLabel(labelTitle,((page*4)+i));
            }
            else anchorParent.getChildren().get(i).setVisible(false);
        }
        setAvailability();
        checkPageButtons();
    }

    /**
     * Switches to previous page
     */
    public void previousPage(){
        setInvisible();
        page--;
        for (int i=0;(i<4);i++){
            if (((page*4)+i) < votings.size()) {
                   Label labelTitle = (Label) ((VBox) ((BorderPane) anchorParent.getChildren().get(i)).getChildren().get(0)).getChildren().get(0);
                   anchorParent.getChildren().get(i).setVisible(true);
                   addToLabel(labelTitle,(page*4)+i);
            }
            else anchorParent.getChildren().get(i).setVisible(false);
        }
        setAvailability();
        checkPageButtons();
    }

    /**
     * Checks if there is next/previous page, and if not, deactivates the buttons
     */
    private void checkPageButtons(){
        if ((page-1) < 0) {
            previousPageButton.setDisable(true);
            previousPageButton.setOpacity(0.5);
        }
        else {
            previousPageButton.setDisable(false);
            previousPageButton.setOpacity(1);
        }
        if ((page+1)<totalPages){
            nextPageButton.setDisable(false);
            nextPageButton.setOpacity(1);
        }
        else {
            nextPageButton.setDisable(true);
            nextPageButton.setOpacity(0.5);
        }
    }

    private void setInvisible(){
        for (int i=0;i<4;i++) {
            anchorParent.getChildren().get(i).setVisible(false);
        }
    }

    /**
     * Sets subtitle to voting buttons, according to its availability dates.
     */
    private void setAvailability() {
        for (int i = 0; i < 4; i++) {
            if (i<(votings.size()-(page*4))) {

                BorderPane pollButton = (BorderPane) anchorParent.getChildren().get(i);
                Label labelAvailable = (Label)((VBox) pollButton.getChildren().get(0)).getChildren().get(1);
                labelAvailable.setStyle("-fx-text-fill: #bbbbbb");
                String availability = "";
                int index = page*4+i;
                if (checkIfAvailable(index)) {

                    if (votings.getVoting(index).getDateTo().isEqual(timeFlow.getDate())){
                        labelAvailable.setStyle("-fx-text-fill: #A22718");
                        availability = "Last chance to vote, voting ends today.";
                    }
                    else {
                        availability = "Available from " + votings.getVoting(index).getDateFrom().format(format) + " - " + votings.getVoting(index).getDateTo().format(format);
                    }

                    pollButton.setDisable(false);
                    pollButton.setOpacity(1);

                } else {

                    if (votings.getVoting(index).getDateTo().isBefore(timeFlow.getDate())){
                        availability = "Voting is over.";
                    }

                    //TODO negative values
                    if (votings.getVoting(index).getDateTo().isAfter(timeFlow.getDate())){
                        int x = timeFlow.getDate().getDayOfYear()-votings.getVoting(i).getDateTo().getDayOfYear();
                        availability = "Voting will start in "+x+" days.";
                    }

                    pollButton.setDisable(true);
                    pollButton.setOpacity(0.5);
                }
                labelAvailable.setText(availability);
            }
        }
    }

    private boolean checkIfAvailable(int i){
      LocalDate today = timeFlow.getDate();
      if (((today.isAfter(votings.getVoting(i).getDateFrom()) && today.isBefore(votings.getVoting(i).getDateTo()))) || (today.isEqual(votings.getVoting(i).getDateFrom())) || (today.isEqual(votings.getVoting(i).getDateTo()))){
          return true;
      }
     else return false;
    }

    private void addToLabel(Label labelTitle, int index){
        labelTitle.setText(votings.getVoting(index).getTitle());
    }

    /**
     * Increases date by one day, and if it's new month, shows last month statistics
     */
    public void nextDay(){
        int thisMonth = timeFlow.getDate().getMonthValue();
        timeFlow.next();
        dateLabel.setText("Today: " +timeFlow.toString());
        setAvailability();
        if (timeFlow.getDate().getMonthValue() > thisMonth){
            String message = "It's a new month! New fresh start!\n\n";
            if (currentUsr.getThisMonthVotings() > 0) {
                 message+="Last month you completed " + currentUsr.getThisMonthVotings() + " votings and totally you have completed "
                         + currentUsr.getCompletedVotings() + " voting/s! " +
                         "\n Votings created this month: "+currentUsr.getThisMonthCreated()+"/"+currentUsr.getTotalCreated()+" (total)"
                         +"\n\nKeep rockin'!";
            }
            else {
                    message+="It's a pity, but you didn't complete any voting last month. \nThough, totally you have completed " +
                            + currentUsr.getCompletedVotings() + " votings!\n" +
                            "Votings created this month: "+currentUsr.getThisMonthCreated()+"/"+currentUsr.getTotalCreated()+" (total)"
                            +"\n\nGood luck, and don't forget to vote :)";

            }
            Warning.showAlert(message,600);
            currentUsr.setThisMonthVotings(0);
            currentUsr.setThisMonthCreated(0);
        }
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
        View.newView("/View/login.fxml", account, "E-vote - Log In","",true);
        Warning.showAlert("You have been successfully logged out");
    }

    private void openPoll(int pane){
        int index = (page*4)+pane;
        if (!(votings.getVoting(index).votedAlready(currentUsr.getEmail()))) {
                View.newView("/View/votingPoll.fxml",account,"E-vote",new votingPollController(votings.getVoting(index), currentUsr.getEmail(), timeFlow.toString(),index,currentUsr.getThisMonthVotings(),timeFlow.getDate()),false);
        }
        else {
            Warning.showAlert("You already completed this voting. One user may vote for each voting only once.");
        }

    }

    public void createVoting() {
        View.newView("/View/addVoting.fxml",account,"E-vote - Add Voting",new addVotingController(currentUsr,timeFlow.getDate()),false);
    }






}
