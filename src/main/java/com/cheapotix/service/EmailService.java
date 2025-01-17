package com.cheapotix.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
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
	private final GameService gameService;
	private final SpringTemplateEngine template;
	
	public EmailService(UserRepository userRepository, GameService gameService, SpringTemplateEngine template) {
		this.userRepository = userRepository;
		this.gameService = gameService;
		this.template = template;
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
			
			List<Game> cheapGames = gameService.getSortedGames(arenaIds, threshhold);
			
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
		
		Context context = new Context();
		context.setVariable("games", cheapGames);
		
		//grab email template from email.html
		String htmlContent = template.process("email", context);

		
		Email from = new Email("cheapotix@gmail.com");
		String subject = "Cheap Tickets Alert!!";
		Email to = new Email(user.getEmail());
		Content content = new Content("text/html", htmlContent);
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