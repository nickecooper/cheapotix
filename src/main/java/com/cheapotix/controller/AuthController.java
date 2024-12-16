package com.cheapotix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cheapotix.service.UserService;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Validated
@Controller
public class AuthController {
	
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String showRegistrationForm() {
		return "register";
	}
	
	public static class RegistrationForm {
		
		//email must be in email format
		@NotBlank
		@Email
		public String email;
		
		//password must be at least mildly complex
		@NotBlank
		@Pattern(
			regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
			message = "Password must be at least 8 characters in length and include an uppercase letter,"
					+ " a lowercase letter, and a number."
		)
		public String password;
		
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("form") @Validated RegistrationForm form,  Model model) {
		if (userService.findByEmail(form.email) != null) {
			model.addAttribute("error", "Email already registered with user");
			return "register";
		}
		
		userService.registerNewUser(form.email, form.password);
		return "redirect:/login?registered";
	}
}
