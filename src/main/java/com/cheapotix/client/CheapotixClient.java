package com.cheapotix.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cheapotix.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CheapotixClient {
	@Value("${ticketmaster.api.url}")
	private String ticketMasterUrl;
	
	@Value("${ticketmaster.api.key}")
	private String ticketMasterKey;
	
	private final RestTemplate restTemplate;
	
	private final GameService gameService;
	
	public CheapotixClient(RestTemplate restTemplate, GameService gameService) {
		this.restTemplate = restTemplate;
		this.gameService = gameService;
	}
	
	public void updateAllGames(List<String> arenaIds) throws JsonMappingException, JsonProcessingException{
		for (String arenaId : arenaIds) {
			//first page of nba games for arena up to 20
			String url = String.format("%s/discovery/v2/events.json?sort=date,asc&subGenreId=KZazBEonSMnZfZ7vFJA&venueId=%s&apikey=%s", 
					ticketMasterUrl, arenaId, ticketMasterKey);
			String response = restTemplate.getForObject(url, String.class);
			parseResponse(arenaId, response);
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			//second page of nba games for arena up to 20
			url = String.format("%s/discovery/v2/events.json?sort=date,asc&subGenreId=KZazBEonSMnZfZ7vFJA&page=1&venueId=%s&apikey=%s", 
					ticketMasterUrl, arenaId, ticketMasterKey);
			response = restTemplate.getForObject(url, String.class);
			parseResponse(arenaId, response);
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void parseResponse(String arenaId, String response) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response);
	
		if (root.has("_embedded") && root.path("_embedded").has("events")) {	
			JsonNode events = root.path("_embedded").path("events");
			if (events.isArray()) {
				for (JsonNode event : events) {
					String id = event.get("id").asText();
					String title = event.get("name").asText();
					System.out.println(title + "");
					System.out.println(arenaId);
					String url = event.get("url").asText();
					double minPrice;
					if (event.get("priceRanges") == null) {
						minPrice = -1;
					}else {
						minPrice = event.get("priceRanges").get(0).get("min").asDouble();
					}
					
					
					String dateString = event.get("dates").get("start").get("localDate").asText();
					String timeString = event.get("dates").get("start").get("localTime").asText();
					LocalDate date = LocalDate.parse(dateString);
					LocalTime time = LocalTime.parse(timeString);
					LocalDateTime dateTime = date.atTime(time);
					
					gameService.addOrUpdateGame(id, title, url, arenaId, minPrice, dateTime);
				}
			}else {
				System.out.println("reading api didn't work... returning");
				return;
			}
		}else {
			System.out.println("reading api didn't work... returning");
			return;
		}
	}
}
