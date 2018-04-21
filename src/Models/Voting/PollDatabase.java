package Models.Voting;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PollDatabase {

    private String path;
    private List<Voting> votings = new ArrayList<>();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PollDatabase(String path){
        String localDir = System.getProperty("user.dir");
        this.path = localDir + path;
    }

    public void loadDatabase(){
        try {
            File f = new File(path);
            BufferedReader rd = new BufferedReader( new FileReader(f));
            String line = "";

            while ((line = rd.readLine())!=null ){
                String[] temp = line.split(";");
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = temp[i].substring(temp[i].indexOf("\"")+1, temp[i].lastIndexOf("\""));
                }
                LocalDate dateFrom = LocalDate.parse(temp[1],format);
                LocalDate dateTo = LocalDate.parse(temp[2],format);
                List<Poll> polls = new ArrayList<>();
                for (int i=4;i<temp.length;i+=4) {
                    polls.add(new Poll(temp[i], temp[i+1], temp[i+2], temp[i+3]));
                }
                addVoting(new Voting(temp[0], Integer.parseInt(temp[3]), polls, dateFrom, dateTo));
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Error loading DATABASE: File not found.");
        }
        catch (IOException e){
            System.err.println(e);
        }
    }

    public void saveToFile(){
        try{
            File f = new File(path);
            BufferedWriter out = new BufferedWriter(new FileWriter(f));

            for (int i=0;i<votings.size();i++) {
                out.write("\""+votings.get(i).getTitle()+"\";\""+votings.get(i).getDateFrom().format(format)+"\";\""+votings.get(i).getDateTo().format(format)+"\";\""+votings.get(i).getPollCounter()+"\";");
                for (int j=0;j<votings.get(i).getPollCounter();j++) {
                    out.write("\""+votings.get(i).getPolls().get(j).getType()+"\";\""+votings.get(i).getPolls().get(j).getQuestion()+"\";\""+votings.get(i).getPolls().get(j).getChoices()[0]+"\";\""+votings.get(i).getPolls().get(j).getChoices()[1]+"\"");
                }
                out.newLine();
            }

            out.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void addVoting(Voting newVoting){
        votings.add(newVoting);
    }

    public Voting getVoting(int index){
        return votings.get(index);
    }

    public int size(){
        return votings.size();
    }


}
