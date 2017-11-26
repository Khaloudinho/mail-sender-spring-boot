package fr.miage.m2.mailsenderspring;

import fr.miage.m2.mailsenderspring.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class MailSenderSpringApplication implements CommandLineRunner {

	@Autowired
	@Qualifier("MailSenderBean")
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
