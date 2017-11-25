package fr.miage.m2.mailsenderspring;

import fr.miage.m2.mailsenderspring.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailSenderSpringApplication implements CommandLineRunner {

	@Autowired
	private MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(MailSenderSpringApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		mailService.send("This Is A Test", "Hello World !");
		mailService.receive();
	}
}
