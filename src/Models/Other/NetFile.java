package Models.Other;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetFile {


    //DONT TOUCH THIS PLS :D :D :D

    public static void download(URL adress,String path) throws IOException {

            URL url = adress;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = null;
            String  filename = url.getFile();
            filename = filename.substring(filename.lastIndexOf('/')+1);
            FileOutputStream outputStream = new FileOutputStream(path + File.separator+ filename);

            inputStream = connection.getInputStream();

            int read = -1;
            byte[] buffer = new byte[4096];

            while((read = inputStream.read(buffer))!= -1){
                outputStream.write(buffer,0,read);
            }
            inputStream.close();
            outputStream.close();
    }
}

