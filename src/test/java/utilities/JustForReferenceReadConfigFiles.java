package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class JustForReferenceReadConfigFiles {

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        String root_path = System.getProperty("user.dir");
        FileReader fr = new FileReader(root_path+"/src/test/configFiles/config.properties");
        Properties prop = new Properties();
        prop.load(fr);

        System.out.println(prop.getProperty("browser"));
        System.out.println(prop.getProperty("testUrl"));

    }
}
