package fr.miage.m2.mailsenderspring.mail;

public interface MailService {

    boolean send(String subject, String content);

    Object receive();
}
