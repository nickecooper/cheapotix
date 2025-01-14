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

@Controller
public class HomeController {
	
	private final CheapotixService cheapotixService;
	private final GameRepository gameRepository;
	private final EmailService emailService;
	
	public HomeController(CheapotixService cheapotixService, GameRepository gameRepository, EmailService emailService) {
		this.cheapotixService = cheapotixService;
		this.gameRepository = gameRepository;
		this.emailService = emailService;
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
		
		List<Game> games = gameRepository.findByArenaIdsAndThreshhold(arenaIds, threshhold);
		
		model.addAttribute("games", games);
		
		return "index";
	}
	
	@PostMapping("/updateGames")
	public String updateGames() throws IOException {
		//cheapotixService.updateGames();
		emailService.chooseEmails();
		return "redirect:/";
	}
}
