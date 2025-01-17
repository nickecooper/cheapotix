package com.cheapotix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cheapotix.client.CheapotixClient;
import com.cheapotix.model.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class CheapotixService {
	
	private final CheapotixClient cheapotixClient;
	
	public CheapotixService(CheapotixClient cheapotixClient) {
		this.cheapotixClient = cheapotixClient;
	}

	public void updateGames() throws JsonMappingException, JsonProcessingException {
		List<String> arenaIds = new ArrayList<>();
		
		// all home arena ids for nba games (in alphabetical order from A-Z)
		arenaIds.add("KovZ917AiMF");
		arenaIds.add("KovZ917A_fV");
		arenaIds.add("KovZpa2M7e");
		arenaIds.add("Z6r9jZ6aAe");
		arenaIds.add("KovZpa2gne");
		arenaIds.add("KovZ917Acq0");
		arenaIds.add("KovZpZAE6vlA");
		arenaIds.add("KovZpa2Xke");
		arenaIds.add("KovZpZAJtFaA");
		arenaIds.add("KovZpZA6AEIA");
		arenaIds.add("Z7r9jZ1A7CK7v");
		arenaIds.add("KovZpZAEF76A");
		arenaIds.add("KovZpZA7AAEA");
		arenaIds.add("KovZpZAEdntA");
		arenaIds.add("KovZpZAEvEEA");
		arenaIds.add("KovZpZAJ67eA");
		arenaIds.add("KovZ917AtP3");
		arenaIds.add("KovZpZAFaJeA");
		arenaIds.add("KovZpZA6keIA");
		arenaIds.add("KovZ917A25V");
		arenaIds.add("Z7r9jZ1A7CaoY");
		arenaIds.add("ZFr9jZ7v7v");
		arenaIds.add("KovZpZAFFE1A");
		arenaIds.add("KovZpZAE617A");
		arenaIds.add("KovZpZAJJEdA");
		arenaIds.add("ZFr9jZe667");
		arenaIds.add("KovZpa2Wre");
		arenaIds.add("KovZpa6MXe");
		arenaIds.add("KovZ917Ah1H");
		arenaIds.add("KovZpaKuJe");
		
		cheapotixClient.updateAllGames(arenaIds);
	}
}
