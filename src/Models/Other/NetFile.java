package Models.Other;


import javafx.application.Platform;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.UnknownHostException;

public class NetFile {

    private static String localDir = System.getProperty("user.dir");
    private static String server = "evote.wz.sk";
    private static String user = "evote.wz.sk";
    private static String pass = "Evote123";


    /**
     * Download file using SFTP Apache Commons Net from my FTP server
     * @param path Relative path to both localfile and file on the server
     */
    public static void download(String path){
                    int port = 21;
                    FTPClient ftpClient = new FTPClient();
                    try {

                            ftpClient.connect(server, port);
                            ftpClient.login(user, pass);
                            ftpClient.enterLocalPassiveMode();
                            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                            String remoteFile = path;
                            File downloadFile = new File(localDir+path);
                            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
                            boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
                            outputStream.close();

                            if (!success) {
                                    Warning.showAlert("Downloading failed. Using local file. Data may be outdated.");
                            }

                    }
                    catch (UnknownHostException e){
                        Warning.showAlert("No internet connection.");
                        Platform.exit();
                    }

                    catch (IOException ex) {
                    }

                    finally {
                            try {
                                    if (ftpClient.isConnected()) {
                                            ftpClient.logout();
                                            ftpClient.disconnect();
                                    }
                            } catch (IOException ex) {
                                    ex.printStackTrace();
                            }
                    }
    }

    /**
     * Upload file using SFTP Apache Commons Net to my FTP server
     * @param path Relative path to both localfile and file on the server
     */
    public static void upload(String path) {
        int port = 21;
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(localDir + path);

            String remoteFile = path;
            InputStream inputStream = new FileInputStream(localFile);

            boolean done = ftpClient.storeFile(remoteFile, inputStream);
            inputStream.close();
            if (!done) {
                Warning.showAlert("Uploading failed. Caused by: NO INTERNET CONNECTION");
            } else {
                //localFile.delete();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}