package fr.miage.m2.mailsenderspring.log;

public class ConsoleLogger implements Logger {

    public ConsoleLogger() {}

    public void log(Object object) {

        System.out.println(object.toString());

    }
}
