package Models.Voting;

import Models.Other.NetFile;
import Models.Other.Warning;
import javafx.application.Platform;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PollDatabase {

    private String localPath;
    private String path;
    private List<Voting> votings = new ArrayList<>();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PollDatabase(String path){
        String localDir = System.getProperty("user.dir");
        this.localPath = localDir+path;
        this.path=path;
    }

    /**
     * Reads data from file and loads it into database
     */
    public void loadDatabase(){
        try {

            NetFile.download(path);

            File f = new File(localPath);
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
                String[] users = temp[4].split(":");


                for (int i=5;i<temp.length;i+=6) {
                    polls.add(new Poll(temp[i], temp[i+1], temp[i+2], temp[i+3],Double.parseDouble(temp[i+4]),Double.parseDouble(temp[i+5])));
                }
                if (!( (users.length==1) && (users[0].equals("")) )) {
                    addVoting(new Voting(temp[0], Integer.parseInt(temp[3]), polls, users, dateFrom, dateTo));
                }
                else {
                    addVoting(new Voting(temp[0], Integer.parseInt(temp[3]), polls, dateFrom, dateTo));
                }

            }
            rd.close();
            //f.delete();
        }
        catch (FileNotFoundException e) {
            Warning.showAlert("File not found. Most likely you have no internet connection. Program will now exit.");
            Platform.exit();
        }
        catch (IOException e){
        }
    }

    /**
     * Saves data to file
     */
    public void saveToFile(){
        try{
            File f = new File(localPath);
            BufferedWriter out = new BufferedWriter(new FileWriter(f));

            for (int i=0;i<votings.size();i++) {
                out.write("\""+votings.get(i).getTitle()+"\";\""+votings.get(i).getDateFrom().format(format)+"\";\""+votings.get(i).getDateTo().format(format)+"\";\""+votings.get(i).getPollCounter()+"\";\"");
                for (int j=0;j<votings.get(i).getVoters().size();j++){
                    if (!(votings.get(i).getVoters().isEmpty())) {
                        out.write(votings.get(i).getVoters().get(j).getEmailHash()+":");
                    }
                }
                out.write("\"");
                for (int j=0;j<votings.get(i).getPollCounter();j++) {
                    out.write(";\""+votings.get(i).getPolls().get(j).getType()+"\";\""+votings.get(i).getPolls().get(j).getQuestion()
                                +"\";\""+votings.get(i).getPolls().get(j).getChoices()[0]+"\";\""+votings.get(i).getPolls().get(j).getChoices()[1]
                                +"\";\""+votings.get(i).getPolls().get(j).getStats()[0]+"\";\""+votings.get(i).getPolls().get(j).getStats()[1]+"\"");
                }
                out.newLine();
            }

            out.close();
            NetFile.upload(path);

        }catch (Exception e){
        }

    }

    public void addVoting(Voting newVoting){
        votings.add(newVoting);
    }

    public void addVoting(int index,Voting voting){
        votings.add(index,voting);
    }

    public Voting getVoting(int index){
        return votings.get(index);
    }

    public int size(){
        return votings.size();
    }


}
