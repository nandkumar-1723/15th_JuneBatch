package Utils;

import java.io.*;
import java.util.*;

/**
 * @author Nandkumar Babar
 */
public class ReadPropFile {

    public static String readPropFile(String key) throws IOException {

        FileReader fr = new FileReader("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/resources/globalValues.properties");
        Properties pr = new Properties();
        pr.load(fr);
        String propValue = pr.getProperty(key).toString();
        return propValue;
    }
}
