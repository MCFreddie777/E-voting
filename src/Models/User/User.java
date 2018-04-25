package Models.User;

public class User {
    private String email;
    private String password;
    private int completedVotings;
    private int thisMonthVotings;
    private int totalCreated;
    private int thisMonthCreated;

    User(String email, String password,int total,int created) {
        setEmail(email);
        setPassword(password);
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setThisMonthCreated(int thisMonthCreated) {
        this.thisMonthCreated = thisMonthCreated;
    }

    public void setTotalCreated(int totalCreated) {
        this.totalCreated = totalCreated;
    }

    public void incCreated() {
        thisMonthCreated++;
        totalCreated++;
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

    public String getPassword() { return password; }

    public void setCompletedVotings(int completedVotings) {
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




}
