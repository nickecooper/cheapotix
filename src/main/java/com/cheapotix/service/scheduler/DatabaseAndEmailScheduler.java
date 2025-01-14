package com.cheapotix.service.scheduler;

import java.io.IOException;
import java.time.LocalTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cheapotix.service.CheapotixService;
import com.cheapotix.service.EmailService;

@Service
public class DatabaseAndEmailScheduler {

	private final CheapotixService cheapotixService;
	private final EmailService emailService;
	
	public DatabaseAndEmailScheduler(CheapotixService cheapotixService, EmailService emailService) {
		this.cheapotixService = cheapotixService;
		this.emailService = emailService;
	}
	
	//updates database of game prices every 5 minutes
	@Scheduled(cron = "0 0 0/6 * * ?")
//	@Scheduled(cron = "0 * * * * ?")
	public void updateDatabase() throws IOException {
		LocalTime time = LocalTime.now();
		System.out.println("**************** Started Updating database at local time: " + time + " ******************************");
		cheapotixService.updateGames();
		System.out.println("**************** Finished updating database either successfully or not ******************************");
		emailService.chooseEmails();
		System.out.println("**************** Finished Sending Email either successfully or not ******************************");
	}
	
}
