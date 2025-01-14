package com.cheapotix.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.cheapotix.model.AppUser;
import com.cheapotix.model.Game;
import com.cheapotix.repository.GameRepository;
import com.cheapotix.repository.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import jakarta.transaction.Transactional;

@Service
public class EmailService {
	
	@Value("${sendgrid.api.key}")
	private String sendGridApiKey;
	
	private final UserRepository userRepository;
	private final GameRepository gameRepository;
	private final JavaMailSender mailSender;
	
	public EmailService(UserRepository userRepository, GameRepository gameRepository, JavaMailSender mailSender) {
		this.userRepository = userRepository;
		this.gameRepository = gameRepository;
		this.mailSender = mailSender;
	}
	
	@Transactional
	public void chooseEmails() throws IOException {
		List<AppUser> users = userRepository.findAll();
		
		for (AppUser user : users) {
			System.out.println(user.getEmail());
			List<String> arenaIds = user.getArenaIds();
			
			if (arenaIds != null) {
				arenaIds = Arrays.stream(arenaIds.get(0).replace("(", "").replace(")", "").split(","))
						.collect(Collectors.toList());
				arenaIds.stream().forEach(a -> System.out.println("*" + a + "*"));
			}
			double threshhold = user.getThreshhold();
			
			List<Game> cheapGames = gameRepository.findByArenaIdsAndThreshhold(arenaIds, threshhold);
			
			if (user.getUpdatesUntilEmail() > 1) {
				user.setUpdatesUntilEmail(user.getUpdatesUntilEmail() - 1);
			} 
			else if(cheapGames.isEmpty()) {
				user.setOnHold(true);
			}
			else {
				user.setUpdatesUntilEmail(user.getEmailFrequency());
				sendEmail(user, cheapGames);
			}
		}
		System.out.println("********************");
	}
	
	public void sendEmail(AppUser user, List<Game> cheapGames) throws IOException {
		
		String header = """
		    Hello Cheapotix User,

		    There are currently some cheap tickets you may be interested in purchasing:
		    
		    """;
		
		StringBuilder sb = new StringBuilder(header);
		for (Game game : cheapGames) {
			sb.append(game.getTitle()).append(", "+ game.getDate()).append(", TicketMaster Link: ").append(game.getTicketsLink()).append(", Minimum Ticket Price: ")
			.append(game.getMinPrice()).append("\n\n");
		}
	
		
		String footer = """
		    
		    Enjoy!

		    Sincerely,
		    Cheapotix
		    """;
		
		sb.append(footer);
		
		String body = sb.toString();
		

		//create the email with the text and send it
//		SimpleMailMessage email = new SimpleMailMessage();		
//		email.setTo(user.getEmail());
//		email.setSubject("Cheap Tickets Alert!!");
//		email.setText(body);
//		mailSender.send(email);
		
		Email from = new Email("cheapotix@gmail.com");
		String subject = "Cheap Tickets Alert!!";
		Email to = new Email(user.getEmail());
		Content content = new Content("text/plain", body);
		Mail mail = new Mail(from, subject, to, content);
		
		SendGrid sg = new SendGrid(sendGridApiKey);
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
		
	}
}