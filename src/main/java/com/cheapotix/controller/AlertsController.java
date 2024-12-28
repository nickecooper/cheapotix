package com.cheapotix.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cheapotix.service.UserService;

import jakarta.validation.constraints.NotBlank;

@Controller
public class AlertsController {
	
	private final UserService userService;
	
	public AlertsController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/alerts")
	public String index(Principal principal, Model model){
		
		//This if else statement determines whether user sees logged in navbar or guest navbar
		if (principal != null) {
			String email = principal.getName();
			model.addAttribute("email", email);
		}else{
			model.addAttribute("email", "guest");
		}
		return "alerts";
	}
	
	public static class SaveSettingsForm {
		
		@NotBlank
		public List<String> arenaIds;
		
		@NotBlank
		public double threshhold;

		public List<String> getArenaIds() {
			return arenaIds;
		}

		public void setArenaIds(List<String> arenaIds) {
			this.arenaIds = arenaIds;
		}

		public double getThreshhold() {
			return threshhold;
		}

		public void setThreshhold(double threshhold) {
			this.threshhold = threshhold;
		}
		
	}
	
	@PostMapping("/savealerts")
	public String saveSettings(@RequestParam(name = "arenaIds") List<String> arenaIds,
            @RequestParam(name = "threshhold") Double threshhold, Principal principal, Model model) throws Exception {
		
		String email;
		if (principal != null) {
			email = principal.getName();
		}else{
			email = "guest";
		}
		userService.updateUserPreferences(email, arenaIds, threshhold);
		return "redirect:/alerts?saved";
	}
}
