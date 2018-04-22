package Models.Voting;

import Models.User.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//This will be the Voting in general which has polls with questions.
//It will be used in an vector of Votings, where user can choose which voting he wants to choose.

public class Voting {
    private String title;
    private int pollCounter;
    private List<Poll> polls = new ArrayList<>();
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private List<User> voters = new ArrayList<>();


    public Voting(String title, int pollCounter, List<Poll> polls, String[] users,LocalDate dateFrom, LocalDate dateTo){
        this.title = title;
        this.pollCounter = pollCounter;
        this.polls = polls;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        for (int i=0;i<users.length;i++){
            addVoter(users[i]);
        }
    }

    public Voting(String title, int pollCounter, List<Poll> polls,LocalDate dateFrom, LocalDate dateTo){
        this.title = title;
        this.pollCounter = pollCounter;
        this.polls = polls;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }


    public void replaceStats(Voting voting){
        this.voters = voting.voters;
        for (int i=0;i<this.polls.size();i++) {
            this.polls.get(i).setStats(voting.polls.get(i).getStats());
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPollCounter(int pollCounter) {
        this.pollCounter = pollCounter;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

     public String getTitle() {
        return title;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public int getPollCounter() {
        return pollCounter;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public int getVoterCount() {
        return voters.size();
    }

    public boolean votedAlready(String username){
        if (username.equals("a")) return false;     //TODO REMOVE TESTING PURPOSES ONLY
        for (int i=0;i<voters.size();i++){
            if (voters.get(i).getEmail().equals(username)) return true;
        }
        return false;
    }

    public void addVoter(String username){
        User tempUser = new User(username);
        voters.add(tempUser);
    }

    public List<User> getVoters(){
        return voters;
    }
}
