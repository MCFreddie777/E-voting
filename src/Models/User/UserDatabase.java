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
    private List<String> database = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public UserDatabase(String path) {
        String localDir = System.getProperty("user.dir");
        this.path = localDir + path;
    }

    private String getPath() {
        return path;
    }

    public User getUserByUserName(String email){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return users.get(i);
            }
        }
            System.out.println("NOBODY FOUND with name "+email);
            return null;
    }

    public int getIndexByUserName(String email){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return i;
            }
        }
        return -1;
    }


    public int isInDatabase(String email, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                if (users.get(i).getPassword().equals(password)) {
                    return 0;
                }
                else return 1;
            }
        }
        return 2;
    }


    public boolean isInDatabase(String email){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) return true;
        }
        return false;
    }

    public void addUser(String email,String password){
        User tempUser = new User(email,password,0,0);
        users.add(tempUser);
    }

    /**
     * This method loads csv file into database of usernames and passwords
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
                System.out.println("EMAIL: "+userData[0]+" HASHED: "+MD5(userData[0])+" again: "+encrypt(userData[0]));
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
                System.out.println("EMAIL: "+users.get(i).getEmail()+" HASHED: "+MD5(users.get(i).getEmail()));
                out.write("\""+users.get(i).getEmail()+"\";\""+users.get(i).getPassword()+"\";\""+users.get(i).getCompletedVotings()+"\";\""+users.get(i).getThisMonthCreated()+"\"");
                out.newLine();
            }

            out.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void updateUser(User user){
        users.set(getIndexByUserName(user.getEmail()),user);
    }

    public String MD5(String md5) {
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


    public static String encrypt(String s){
        MessageDigest m= null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(s.getBytes(),0,s.length());
        String encrypted = new java.math.BigInteger(1,m.digest()).toString(16);
        return encrypted;
    }

    
}

