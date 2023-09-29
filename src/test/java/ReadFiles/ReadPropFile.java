package ReadFiles;

import java.io.*;
import java.util.*;

/**
 * @author Nandkumar Babar
 */
public class ReadPropFile {
    public static void main(String[] args) throws IOException {


        //We read the file path
        FileReader fr = new FileReader("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/resources/test.properties");

        // We need to read data as well
        Properties prop =new Properties();
        prop.load(fr);

        //To read the specific key
        System.out.println(prop.getProperty("name"));

        System.out.println(prop.getProperty("fruit"));



    }
}
