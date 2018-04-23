package Models.Voting;

//This is class for poll question in voting. Each voting has it's custom number of polls.
//Polls can be only question polls or image polls.
//The statistics will be in percents and will be shown to voter after voting.


public class Poll {
    private String type;
    private String question;
    private String[] choices = new String[2];
    private double[] stats = new double[2];


    public Poll(String type,String question, String choice1,String choice2,double stat1, double stat2) {
        setType(type);
        setQuestion(question);
        setChoices(choice1,choice2);
        setStats(stat1,stat2);
    }


    private void setType(String type){
        this.type = type;
    }
    private void setQuestion(String question){
        this.question = question;
    }



    private void setChoices(String choice1,String choice2) {
        choices[0] = choice1;
        choices[1] = choice2;
    }

    public void setStats(double stat1,double stat2){
        stats[0] = stat1;
        stats[1] = stat2;
    }

    public void setStats(double[] stats){
        for (int i=0;i<stats.length;i++){
            this.stats=stats;
        }
    }


    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public double[] getStats() {
        return stats;
    }

    public String toString(){
        return (" Type: "+type+" Question: "+question+" Choice1: "+choices[0]+" Choice2: "+choices[1]);
    }
}





