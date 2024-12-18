package com.cheapotix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cheapotix.model.AppUser;
import com.cheapotix.service.UserService;

@Configuration
public class SecurityConfig {

	
	@Bean
	public UserDetailsService userDetailsService(UserService userService) {
		return email -> {
			AppUser user = userService.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("User not found");
			}
			return User.withUsername(user.getEmail())
					.password(user.getPassword())
					.authorities("USER")
					.build();
		};
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/register", "/login", "/css/**", "js/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(login -> login
						.loginPage("/login")
						.defaultSuccessUrl("/", true)
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.permitAll()
				)
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
