package Models.User;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class UserDatabase {
    private String path;
    private List<User> users = new ArrayList<>();

    public UserDatabase(String path) {
        String localDir = System.getProperty("user.dir");
        this.path = localDir + path;
    }

    private String getPath() {
        return path;
    }


    public User getUserByUsername(String email){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailHash().equals(MD5(email))) {
                return users.get(i);
            }
        }
        return null;
    }

    public User getUserByUserHash(String hash){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailHash().equals(hash)) {
                return users.get(i);
            }
        }
        return null;
    }

    public int getIndexByUserHash(String hash){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailHash().equals(hash)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Compares hashes from email user input with ones already in database
     * @param email String e-mail address filled in by user
     * @param password  password
     * @return 0 if email and password hash is in database, 1 if email is, but password is not, 2 in case that neither of those is in database
     */
    public int isInDatabase(String email, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailHash().equals(MD5(email))) {
                if (users.get(i).getPasswordHash().equals(MD5(password))) {
                    return 0;
                }
                else return 1;
            }
        }
        return 2;
    }


    public boolean isInDatabase(String email){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmailHash().equals(MD5(email))) return true;
        }
        return false;
    }

    public void addUser(String email,String password){
        User tempUser = new User(MD5(email),MD5(password),0,0);
        tempUser.setEmail(email);
        users.add(tempUser);
    }

    /**
     * This method loads csv file into database of users
     */
    public void loadDatabase() {
         try {
            File f = new File(this.getPath());
            BufferedReader rd = new BufferedReader( new FileReader(f));
            String line = "";

            while ((line = rd.readLine())!=null ){
                 String[] userData = line.split(";");
                 for (int i = 0; i < userData.length; i++) {
                     userData[i] = userData[i].substring(userData[i].indexOf("\"") + 1, userData[i].lastIndexOf("\""));
                 }
                users.add(new User(userData[0], userData[1],Integer.parseInt(userData[2]),Integer.parseInt(userData[3])));
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Error loading DATABASE: File not found.");
        }
        catch (IOException e){
             System.err.println(e);
        }

    }

    public void saveToFile() {
        try{
            File f = new File(this.getPath());
            BufferedWriter out = new BufferedWriter(new FileWriter(f));

            for (int i=0;i<users.size();i++) {
                out.write("\""+users.get(i).getEmailHash()+"\";\""+users.get(i).getPasswordHash()+"\";\""+users.get(i).getCompletedVotings()+"\";\""+users.get(i).getTotalCreated()+"\"");
                out.newLine();
            }

            out.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Replaces user data from database with new user data
     * @param user User object with new data
     */
    public void updateUser(User user){
        users.set(getIndexByUserHash(user.getEmailHash()),user);
    }


    /**
     * Encrypts string parameter using md5 hash
     * @param md5 String which is going to be encrypted
     * @return encrypted string
     */
    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public String toString(){
        String s = "";
        for (int i=0;i<users.size();i++) s+=users.get(i).toString()+"\n";
        return s;
    }




}

