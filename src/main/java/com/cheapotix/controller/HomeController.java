package com.cheapotix.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheapotix.model.Game;
import com.cheapotix.repository.GameRepository;
import com.cheapotix.service.CheapotixService;
import com.cheapotix.service.EmailService;
import com.cheapotix.service.GameService;

@Controller
public class HomeController {
	
	private final CheapotixService cheapotixService;
	private final EmailService emailService;
	private final GameService gameService;
	
	public HomeController(CheapotixService cheapotixService, EmailService emailService, GameService gameService) {
		this.cheapotixService = cheapotixService;
		this.emailService = emailService;
		this.gameService = gameService;
	}

	@GetMapping("/")
	public String index(Principal principal, Model model){
		
		//This if else statement determines whether user sees logged in navbar or guest navbar
		if (principal != null) {
			String email = principal.getName();
			model.addAttribute("email", email);
		}else{
			model.addAttribute("email", "guest");
		}
		return "index";
	}
	
	@GetMapping("/returnGames")
    public String returnGames(
            @RequestParam(name = "arenaIds", required = false) List<String> arenaIds,
            @RequestParam(name = "threshhold") Double threshhold,
            Model model) {
		
		List<Game> games = gameService.getSortedGames(arenaIds, threshhold);
		
		model.addAttribute("games", games);
		
		return "index";
	}
	
// comment out test route for testing sending out emails	
	
//	@PostMapping("/updateGames")
//	public String updateGames() throws IOException {
//		//cheapotixService.updateGames();
//		emailService.chooseEmails();
//		return "redirect:/";
//	}
}
