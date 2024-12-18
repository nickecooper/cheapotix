package com.cheapotix.controller;

import java.security.Principal;

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
	public String showRegistrationForm(Principal principal, Model model) {
		if (principal != null) {
			String email = principal.getName();
			model.addAttribute("email", email);
		}else{
			model.addAttribute("email", "guest");
		}
		model.addAttribute("form", new RegistrationForm());
		return "register";
	}
	
	@GetMapping("/login")
	public String showLoginPage(Principal principal, Model model) {
		if (principal != null) {
			String email = principal.getName();
			model.addAttribute("email", email);
		}else{
			model.addAttribute("email", "guest");
		}
		return "login";
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
		
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
