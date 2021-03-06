package fr.miage.m2.mailsenderspring.aspect;

import fr.miage.m2.mailsenderspring.log.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    public static void log (String message) {

        try {

            Class classFileLogger = Class.forName("fr.miage.m2.mailsenderspring.log.FileLogger");
            Class classConsoleLogger = Class.forName("fr.miage.m2.mailsenderspring.log.ConsoleLogger");
            Logger fileLogger = (Logger) classFileLogger.newInstance();
            Logger consoleLogger = (Logger) classConsoleLogger.newInstance();
            fileLogger.log(message);
            consoleLogger.log(message);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Before("execution(* fr.miage.m2.mailsenderspring.mail.MailSender.send (java.lang.String, java.lang.String)) && args(subject, content) ")
    public void beforeSendMail(String subject, String content) {
        log("A mail has been sent : \n " + subject + " \n " + content + "\n");
    }

    @Before("execution(* fr.miage.m2.mailsenderspring.mail.MailSender.receive ()) && args() ")
    public void beforeReadMails() {
        log("We are reading the mails, please wait...");
    }

}