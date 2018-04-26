package Models.User;

public class User {
    private String email;
    private String emailHash;
    private String passwordHash;
    private int completedVotings;
    private int thisMonthVotings;
    private int totalCreated;
    private int thisMonthCreated;

    User(String emailHash, String passwordHash ,int total,int created) {
        setEmailHash(emailHash);
        setPasswordHash(passwordHash);
        setCompletedVotings(total);
        setThisMonthVotings(0);
        setTotalCreated(created);
    }

    public User(String email){
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setThisMonthCreated(int thisMonthCreated) {
        this.thisMonthCreated = thisMonthCreated;
    }

    private void setTotalCreated(int totalCreated) {
        this.totalCreated = totalCreated;
    }

    public void incCreated() {
        thisMonthCreated++;
        totalCreated++;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmailHash() {
        return emailHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getThisMonthCreated() {
        return thisMonthCreated;
    }

    public int getTotalCreated() {
        return totalCreated;
    }

    public String getEmail() {
        return email;
    }

    private void setCompletedVotings(int completedVotings) {
        this.completedVotings = completedVotings;
    }

    public void setThisMonthVotings(int thisMonthVotings) {
        this.thisMonthVotings = thisMonthVotings;
    }

    public int getCompletedVotings() {
        return completedVotings;
    }

    public int getThisMonthVotings() {
        return thisMonthVotings;
    }

    public void addCompletedVoting(){
        this.thisMonthVotings++;
        this.completedVotings++;
    }

    public void addCreated(){
        this.thisMonthCreated++;
        this.totalCreated++;
    }

    public String toString(){
        String s = "User: ";
        s+=" "+getEmailHash()+" monthVot "+getThisMonthVotings()+" totalVot "+getCompletedVotings()+" monthCrea "+getThisMonthCreated()+" totalCrea "+getTotalCreated();
        return s;
    }




}
