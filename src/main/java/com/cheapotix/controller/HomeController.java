package com.cheapotix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cheapotix.service.CheapotixService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class HomeController {
	
	private final CheapotixService cheapotixService;
	
	public HomeController(CheapotixService cheapotixService) {
		this.cheapotixService = cheapotixService;
	}

	@GetMapping("/")
	public String index() throws JsonMappingException, JsonProcessingException {
		cheapotixService.updateGames();
		return "index";
	}
}
