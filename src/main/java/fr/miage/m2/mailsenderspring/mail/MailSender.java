package fr.miage.m2.mailsenderspring.mail;

import fr.miage.m2.mailsenderspring.log.ConsoleLogger;
import fr.miage.m2.mailsenderspring.log.FileLogger;
import fr.miage.m2.mailsenderspring.log.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Configuration
public class MailSender implements MailService {

    private Logger fileLogger = new FileLogger();
    private Logger consoleLogger = new ConsoleLogger();

    @Value("${mail.from}")
    private String from;

    @Value("${mail.to}")
    private String to;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp}")
    private String smtp;

    @Value("${mail.pop3}")
    private String pop3;

    @Bean(name = "MailSenderBean")
    public MailSender MailSender(){
        return new MailSender();
    }

    public Properties initClientSMTPConf(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.smtp);
        return props;
    }

    public Properties initClientPOP3Conf(){
        Properties props = new Properties();
        props.put("mail.pop3.host", this.host);
        props.put("mail.pop3.port", this.pop3);
        props.put("mail.pop3.starttls.enable", "true");
        return props;
    }

    public boolean send(String subject, String content) {
        final String username = this.username;
        final String password = this.password;

        // Get the Session object.
        Session session = Session.getInstance(initClientSMTPConf(),
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set parameters related to the fr.miage.m2.mail that would be sent.
            message.setFrom(new InternetAddress(this.from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            // Send message
            Transport.send(message);

            System.out.println("The message has been sent without any problem !");

            consoleLogger.log("Mail with following object << " + subject + " >> has been sent !");
            fileLogger.log("Mail with following object << " + subject + " >> has been sent !");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Object receive() {

        try {

            Session emailSession = Session.getInstance(initClientPOP3Conf());
            Store store = emailSession.getStore("pop3");

            store.connect(this.host, this.username, this.password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            fileLogger.log("\n======================================================\n" +
                    "You have " + messages.length + " messages in the mailbox ! \n");
            consoleLogger.log("\n=====================================================\n" +
                    "You have " + messages.length + " messages in the mailbox ! \n");

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                fileLogger.log("---------------------------------\n");
                fileLogger.log("Email Number " + (i + 1) + "\n");
                fileLogger.log("Subject: " + message.getSubject() + "\n");
                fileLogger.log("From: " + message.getFrom()[0] + "\n");
                fileLogger.log("Text: " + message.getContent().toString());

                consoleLogger.log("---------------------------------");
                consoleLogger.log("Email Number " + (i + 1));
                consoleLogger.log("Subject: " + message.getSubject());
                consoleLogger.log("From: " + message.getFrom()[0]);
                consoleLogger.log("Text: " + message.getContent().toString());
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setPop3(String pop3) {
        this.pop3 = pop3;
    }

    public String getPop3() {
        return pop3;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }
}
