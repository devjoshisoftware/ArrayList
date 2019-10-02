package Group;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProps {
    static Properties props;

    static FileInputStream inputStream;
    //method
    public String getProperty (String key)
    {
        props = new Properties();
        try {
            inputStream = new FileInputStream("src\\main\\Resources\\BrowserDriver\\testdataconfig.properties.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty(key);
    }
}

