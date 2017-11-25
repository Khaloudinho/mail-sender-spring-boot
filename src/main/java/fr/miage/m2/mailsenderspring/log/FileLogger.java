package fr.miage.m2.mailsenderspring.log;

import java.io.*;

public class FileLogger implements Logger {

    private static String text;

    public FileLogger() { }

    public void log(Object object) {

        try {

            FileOutputStream f = new FileOutputStream(new File("logfile.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            text += object.toString();
            o.writeObject(text);

            o.close();
            f.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
