package lib.RequestFactory.ClassRequest.Configuration.AccessLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AccessLayer {

    public static String getApiKey() throws IOException {
        File prFile = new File("./Lib/config.properties");
        Properties properties = new Properties();
        if (prFile.exists()) {
            FileInputStream fileInput = new FileInputStream(prFile);
            properties.load(fileInput);
            fileInput.close();
            String apiKey = properties.getProperty("privateKey");
            return  apiKey;
        }else if (!prFile.exists()) {return "a6e4fd51dc734bb7b44bc1c49d3e0479";}
        return null;
    }
}
